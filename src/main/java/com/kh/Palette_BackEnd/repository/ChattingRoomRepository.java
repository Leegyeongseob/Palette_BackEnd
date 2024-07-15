package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChattingRoomRepository extends JpaRepository<ChatRoomEntity,String> {
    List<ChatRoomEntity> findAll();
}
