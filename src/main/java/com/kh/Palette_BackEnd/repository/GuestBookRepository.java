package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestBookRepository extends JpaRepository<GuestBookEntity, Long> {
    // 특정 커플의 방명록 조회
    List<GuestBookEntity> findByCoupleCoupleName(String coupleName);

    // 특정 사용자의 방명록 조회
    List<GuestBookEntity> findByMemberEmail(String email);

}
