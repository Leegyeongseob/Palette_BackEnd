package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChattingRoomRepository chattingRoomRepository;

    @Transactional
    public ResponseEntity<String> deleteRoom(String roomId) {
        try {
            // 채팅방 삭제 로직: ChattingRoomRepository를 사용하여 데이터베이스에서 삭제
            chattingRoomRepository.deleteById(roomId);

            // 채팅방 삭제 성공 시 메시지 반환
            return ResponseEntity.ok("Room deleted successfully");
        } catch (Exception e) {
            // 예외 발생 시 실패 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete room: " + e.getMessage());
        }
    }
}
