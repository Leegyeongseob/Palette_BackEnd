package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
}
