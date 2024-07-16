package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChatRoomEntity,String> {
    List<ChatRoomEntity> findAll();


    ChatRoomEntity deleteByRoomId(String chatRoomEntity);
}
