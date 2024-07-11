package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
