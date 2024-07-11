package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.entity.PaymentEntity;
import com.kh.Palette_BackEnd.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/complete")
    public PaymentEntity completePayment(@RequestBody PaymentReqDto paymentReqDto) {
        return paymentService.savePayment(paymentReqDto);
    }
}
