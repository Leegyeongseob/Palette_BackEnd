package com.kh.Palette_BackEnd.dto.reqdto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReqDto {
    private String paymentId;
    private String orderName;
    private int totalAmount;
    private Customer customer;

    // getters 및 setters

    @Getter
    @Setter
    public static class Customer {
        private String fullName;
        private String phoneNumber;
        private String email;

        // getters 및 setters
    }
}