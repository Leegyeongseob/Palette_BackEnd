package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity,Long> {

}
