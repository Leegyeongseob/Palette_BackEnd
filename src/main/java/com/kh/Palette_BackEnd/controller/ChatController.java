package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessageDto chatMessageDto) {
        chatService.sendMessageToUser(chatMessageDto.getSender(), chatMessageDto.getReceiver(), chatMessageDto);
        return ResponseEntity.ok("Message sent and saved");
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatEntity>> getChatMessages(@RequestParam String sender, @RequestParam String receiver) {
        List<ChatEntity> messages = chatService.getChatMessages(sender, receiver);
        return ResponseEntity.ok(messages);
    }
}
