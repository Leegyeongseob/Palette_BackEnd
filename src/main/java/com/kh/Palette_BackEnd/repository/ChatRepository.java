package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Long, ChatEntity> {
}
