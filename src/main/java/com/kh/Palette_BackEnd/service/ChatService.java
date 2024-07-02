package com.kh.Palette_BackEnd.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    public void addUserSession(String userId, WebSocketSession session) {
        userSessions.put(userId, session);
        log.debug("New session added for user {}: {}", userId, session);
    }

    public void removeUserSession(String userId) {
        userSessions.remove(userId);
        log.debug("Session removed for user {}: {}", userId);
    }

    public void sendMessageToUser(String senderId, String receiverId, ChatMessageDto message) {
        WebSocketSession receiverSession = userSessions.get(receiverId);
        if (receiverSession != null && receiverSession.isOpen()) {
            sendMessage(receiverSession, message);
        }
        saveMessage(message);
    }

    public void saveMessage(ChatMessageDto messageDto) {
        ChatEntity chatEntity = ChatEntity.builder()
                .sender(messageDto.getSender())
                .receiver(messageDto.getReceiver())
                .chatData(messageDto.getMessage())
                .regDate(LocalDateTime.now())
                .build();
        chatRepository.save(chatEntity);
    }

    public List<ChatEntity> getChatMessages(String sender, String receiver) {
        return chatRepository.findBySenderAndReceiverOrderByRegDateAsc(sender, receiver);
    }

    private <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}