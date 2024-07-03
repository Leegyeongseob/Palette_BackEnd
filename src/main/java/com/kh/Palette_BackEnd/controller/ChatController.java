package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        return ResponseEntity.ok(chatService.getChatMessages(sender, receiver));
    }

    //커플이름 뽑아오기
    @PostMapping("/coupleEmail")
    public ResponseEntity<List<String>> coupleEmail(@RequestBody Map<String,String> email) {
        return ResponseEntity.ok(chatService.coupleEmail(email.get("email")));
    }
}
