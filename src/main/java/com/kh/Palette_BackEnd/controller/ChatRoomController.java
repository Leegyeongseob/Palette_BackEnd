package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // DELETE 요청을 처리하는 핸들러 메서드
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomId) {
        return chatRoomService.deleteRoom(roomId);
    }
}
