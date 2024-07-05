package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryCheckListRepository extends JpaRepository<DiaryCheckListEntity,Long> {
}
