package com.kh.Palette_BackEnd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;
import com.kh.Palette_BackEnd.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<WebSocketSession, String> sessionUserIdMap = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.warn("{}", payload);
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        String userId = chatMessage.getSender();
        sessionUserIdMap.put(session, userId);

        if (chatMessage.getType() == ChatMessageDto.MessageType.ENTER) {
            chatService.addUserSession(userId, session);
        } else if (chatMessage.getType() == ChatMessageDto.MessageType.TALK) {
            chatService.sendMessageToUser(chatMessage.getSender(), chatMessage.getReceiver(), chatMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.warn("afterConnectionClosed : {}", session);
        String userId = sessionUserIdMap.remove(session);
        if (userId != null) {
            chatService.removeUserSession(userId);
        }
    }
}
