package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GalleryEntity;
import com.kh.Palette_BackEnd.entity.GalleryListEntity;
import com.kh.Palette_BackEnd.entity.PaymentEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @PersistenceContext
    EntityManager em;

    public int savePayment(PaymentReqDto paymentRequestDto) {
        Optional<PaymentEntity> paymentEntityOpt = paymentRepository.findbyCustomerEmail(paymentRequestDto.getCustomerEmail());
        if(paymentEntityOpt.isPresent())
        {
            PaymentEntity paymentEntity = paymentEntityOpt.get();
            paymentEntity.setTotalAmount(paymentEntity.getTotalAmount() + paymentRequestDto.getTotalAmount());
            paymentRepository.saveAndFlush(paymentEntity);
            em.clear();
            return paymentEntity.getTotalAmount();
        }
        else{
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
            return payment.getTotalAmount();
        }

    }
}
