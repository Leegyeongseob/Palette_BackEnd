package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Payment_TB")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentId;
    private String orderName;
    private int totalAmount;
    @Embedded
    private Customer customer;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Customer {
        private String fullName;
        private String phoneNumber;
        private String email;
    }
}
