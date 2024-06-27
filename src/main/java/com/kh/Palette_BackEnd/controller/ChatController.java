package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.repository.ChatRepository;
import com.kh.Palette_BackEnd.reqDto.ChatRoomReqDto;
import lombok.RequiredArgsConstructor;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.Palette_BackEnd.resdto.ChatRoomResDto;
import com.kh.Palette_BackEnd.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomDto) {
        log.warn("chatRoomDto : {}", chatRoomDto);
        ChatRoomResDto room = chatService.createRoom(chatRoomDto.getName());
        System.out.println(room.getRoomId());
        return ResponseEntity.ok(room.getRoomId());
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessageDto chatMessageDto) {
        chatService.sendMessageToAll(chatMessageDto.getRoomId(), chatMessageDto);
        chatService.saveMessage(chatMessageDto);
        return ResponseEntity.ok("Message sent and saved");
    }
    
    @GetMapping("/messages")
    public ResponseEntity<List<ChatEntity>> getChatMessages(@RequestParam String roomId) {
        List<ChatEntity> messages = chatService.getChatMessagesByRoomId(roomId);
        return ResponseEntity.ok(messages);
    }

    // 방 정보 가져오기
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomResDto> findRoomById(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.findRoomById(roomId));
    }
}