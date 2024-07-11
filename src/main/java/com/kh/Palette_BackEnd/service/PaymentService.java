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

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentEntity savePayment(PaymentReqDto paymentRequestDto) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(paymentRequestDto.getPaymentId());
        payment.setOrderName(paymentRequestDto.getOrderName());
        payment.setTotalAmount(paymentRequestDto.getTotalAmount());
        payment.setCustomerName(paymentRequestDto.getCustomerName());
        payment.setCustomerPhone(paymentRequestDto.getCustomerPhone());
        payment.setCustomerEmail(paymentRequestDto.getCustomerEmail());
        payment.setStatus(paymentRequestDto.getStatus());
        return paymentRepository.save(payment);
    }
}
