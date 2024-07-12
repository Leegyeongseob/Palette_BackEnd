package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findbyCustomerEmail(String email);
}
