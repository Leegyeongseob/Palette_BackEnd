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

    public void sendMessageToUser(String sender, String receiver, ChatMessageDto message) {
        WebSocketSession receiverSession = userSessions.get(receiver);
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
        log.debug("받은 이메일: {}", email);
        List<String> emailList = new ArrayList<>();
        try {
            Optional<CoupleEntity> coupleEntityOptional = coupleRepository.findByFirstEmailOrSecondEmail(email, email);

            coupleEntityOptional.ifPresent(coupleEntity -> {
                String firstEmail = coupleEntity.getFirstEmail();
                String secondEmail = coupleEntity.getSecondEmail();
                log.debug("커플 엔터티 발견: {}, {}", firstEmail, secondEmail);

                if (email.equals(firstEmail)) {
                    emailList.add(firstEmail);
                    emailList.add(secondEmail);
                } else if (email.equals(secondEmail)) {
                    emailList.add(secondEmail);
                    emailList.add(firstEmail);
                }
            });

            if (!coupleEntityOptional.isPresent()) {
                log.debug("이메일에 해당하는 커플 엔터티를 찾을 수 없음: {}", email);
            }
        } catch (Exception e) {
            log.error("커플 이메일 가져오는 중 오류 발생", e);
        }
        return emailList;
    }


}