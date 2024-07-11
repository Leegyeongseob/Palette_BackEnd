package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.entity.GalleryEntity;
import com.kh.Palette_BackEnd.entity.PaymentEntity;
import com.kh.Palette_BackEnd.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping("/complete")
//    public ResponseEntity<PaymentEntity> completePayment(@RequestBody PaymentReqDto paymentReqDto) {
//        PaymentEntity savePayment = paymentService.savePayment(paymentReqDto);
//        return ResponseEntity.ok(savePayment);
//    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody PaymentReqDto paymentReqDto) {
        PaymentEntity paymentEntity = paymentService.savePayment(paymentReqDto);
        return ResponseEntity.ok("Payment data saved successfully.");
    }
}
