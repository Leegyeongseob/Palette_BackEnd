package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="payment_TB")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentId;
    private String orderName;
    private int totalAmount;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String status;
}
