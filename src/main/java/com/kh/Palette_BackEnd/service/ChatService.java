package com.kh.Palette_BackEnd.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.repository.ChatRepository;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;
    private final CoupleRepository coupleRepository;
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
    public List<String> coupleEmail(String email) {
        Optional<CoupleEntity> coupleEntity = coupleRepository.findByFirstEmail(email);
        List<String> list = new ArrayList<>();

        if (coupleEntity.isPresent()) {
            // 만약 첫 번째 계정이 동일한 경우
            if (email.equals(coupleEntity.get().getFirstEmail())) {
                String secondEmail = coupleEntity.get().getSecondEmail();
                list.add(email);
                list.add(secondEmail);
            } else { // 두 번째 계정이 동일한 경우
                String firstEmail = coupleEntity.get().getFirstEmail();
                list.add(email);
                list.add(firstEmail);
            }
            return list;
        }

        return list; // 커플 정보가 없으면 빈 리스트 반환
    }
}