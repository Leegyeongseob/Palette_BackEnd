package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findBySenderAndReceiverOrderByRegDateAsc(String sender, String receiver);
}