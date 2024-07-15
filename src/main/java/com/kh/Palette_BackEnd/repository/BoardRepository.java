package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findByCouple_FirstEmailAndCouple_SecondEmailOrderByRegDateDesc(String firstEmail, String secondEmail, Pageable pageable);
}
