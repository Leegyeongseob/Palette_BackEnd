package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.PaymentReqDto;
import com.kh.Palette_BackEnd.dto.resdto.PaymentResDto;
import com.kh.Palette_BackEnd.entity.GalleryEntity;
import com.kh.Palette_BackEnd.entity.PaymentEntity;
import com.kh.Palette_BackEnd.resdto.GalleryResDto;
import com.kh.Palette_BackEnd.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody PaymentReqDto paymentReqDto) {
        String total_price = paymentService.savePayment(paymentReqDto);
        return ResponseEntity.ok(total_price);
    }

    @GetMapping("/amount")
    public ResponseEntity<Integer> getAmount(@RequestParam String email) {
        int total_amount = paymentService.getAmount(email);
        return ResponseEntity.ok(total_amount);
    }
}
