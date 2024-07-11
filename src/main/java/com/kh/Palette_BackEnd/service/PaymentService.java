package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.entity.PaymentEntity;
import com.kh.Palette_BackEnd.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentEntity savePayment(PaymentReqDto paymentReqDto) {
        // PaymentEntity.Customer 인스턴스 생성
        PaymentEntity.Customer customer = PaymentEntity.Customer.builder()
                .fullName(paymentReqDto.getCustomer().getFullName())
                .phoneNumber(paymentReqDto.getCustomer().getPhoneNumber())
                .email(paymentReqDto.getCustomer().getEmail())
                .build();

        // PaymentEntity 인스턴스 생성
        PaymentEntity payment = PaymentEntity.builder()
                .paymentId(paymentReqDto.getPaymentId())
                .orderName(paymentReqDto.getOrderName())
                .totalAmount(paymentReqDto.getTotalAmount())
                .customer(customer)
                .build();

        return paymentRepository.save(payment);
    }
}
