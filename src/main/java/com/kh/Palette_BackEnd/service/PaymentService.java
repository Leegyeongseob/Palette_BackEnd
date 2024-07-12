package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.dto.resdto.PaymentResDto;
import com.kh.Palette_BackEnd.entity.*;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import com.kh.Palette_BackEnd.repository.PaymentRepository;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import com.kh.Palette_BackEnd.resdto.GalleryResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private DiaryRepository diaryRepository;
    private CoupleRepository coupleRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @PersistenceContext
    EntityManager em;

    public String savePayment(PaymentReqDto paymentRequestDto) {
        try {
            Optional<CoupleEntity> coupleOpt = coupleRepository.findByFirstEmailOrSecondEmail(paymentRequestDto.getCustomerEmail(), paymentRequestDto.getCustomerEmail());
            CoupleEntity couple = coupleOpt.orElseThrow(() -> new RuntimeException("Couple not found"));
            Integer totalAmount = paymentRequestDto.getTotalAmount();

            Optional<PaymentEntity> existingPaymentOpt = paymentRepository.findByCoupleAndTotalAmount(couple, totalAmount);
            if (existingPaymentOpt.isPresent()) {
                PaymentEntity paymentEntity = existingPaymentOpt.get();
                paymentEntity.setTotalAmount(paymentEntity.getTotalAmount() + paymentRequestDto.getTotalAmount());
                paymentRepository.saveAndFlush(paymentEntity);
                em.clear();
                return "기존 구매값에 더해졌습니다.";
            } else {
                PaymentEntity payment = new PaymentEntity();
                payment.setPaymentId(paymentRequestDto.getPaymentId());
                payment.setOrderName(paymentRequestDto.getOrderName());
                payment.setTotalAmount(paymentRequestDto.getTotalAmount());
                payment.setCustomerName(paymentRequestDto.getCustomerName());
                payment.setCustomerPhone(paymentRequestDto.getCustomerPhone());
                payment.setCustomerEmail(paymentRequestDto.getCustomerEmail());
                payment.setStatus(paymentRequestDto.getStatus());
                paymentRepository.saveAndFlush(payment);
                em.clear();
                return "신규 구매 값이 추가 되었습니다.";
            }
        }
        catch (Exception e) {
            log.error("Failed to save diary: {}", e.getMessage());
            throw new RuntimeException("Failed to save gallery", e);
        }
    }
    @Transactional
    public Integer getAmount (String email) {
        CoupleEntity couple = coupleRepository.findByFirstEmailOrSecondEmail(email, email)
                .orElseThrow(() -> new RuntimeException("Couple not found"));

        PaymentEntity totalAmount = paymentRepository.findByCouple(couple);
        return totalAmount.getTotalAmount();
    }
}
