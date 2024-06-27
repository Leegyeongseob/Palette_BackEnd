package com.kh.Palette_BackEnd.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.Palette_BackEnd.dto.ChatMessageDto;

import com.kh.Palette_BackEnd.entity.ChatEntity;
import com.kh.Palette_BackEnd.repository.ChatRepository;
import com.kh.Palette_BackEnd.resdto.ChatRoomResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper; // JSON 문자열로 변환하기 위한 객체
    private final ChatRepository chatRepository;
    private Map<String, ChatRoomResDto> chatRooms; // 채팅방 정보를 담을 맵

    @PostConstruct // 의존성 주입 이후 초기화를 수행하는 메소드
    private void init() { // 채팅방 정보를 담을 맵을 초기화
        chatRooms = new LinkedHashMap<>(); // 채팅방 정보를 담을 맵
    }
    public List<ChatRoomResDto> findAllRoom() { // 채팅방 리스트 반환
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomResDto findRoomById(String email) {return chatRooms.get(email);
    }

    // 방 개설하기
    public ChatRoomResDto createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        log.info("UUID : " + randomId);
        ChatRoomResDto chatRoom = ChatRoomResDto.builder() // 채팅방 생성
                .roomId(randomId)
                .name(name)
                .regDate(LocalDateTime.now())
                .build();
        chatRooms.put(randomId, chatRoom);  // 방 생성, 키를 UUID로 하고 방 정보를 값으로 저장
        return chatRoom;
    }

    public void removeRoom(String email) { // 방 삭제
        ChatRoomResDto room = chatRooms.get(email); // 방 정보 가져오기
        if (room != null) { // 방이 존재하면
            if (room.isSessionEmpty()) { // 방에 세션이 없으면
                chatRooms.remove(email); // 방 삭제
            }
        }
    }

    //  채팅방에 입장한 세션 추가
    public void addSessionAndHandleEnter(String email, WebSocketSession session, ChatMessageDto chatMessage) {
        ChatRoomResDto room = findRoomById(email);
        if (room != null) {
            room.getSessions().add(session); // 채팅방에 입장한 세션 추가

            log.debug("New session added: " + session);
        }
    }

    // 채팅방에서 퇴장한 세션 제거
    public void removeSessionAndHandleExit(String email, WebSocketSession session, ChatMessageDto chatMessage) {
        ChatRoomResDto room = findRoomById(email); // 채팅방 정보 가져오기
        if (room != null) {
            room.getSessions().remove(session); // 채팅방에서 퇴장한 세션 제거

            log.debug("Session removed: " + session);
            if (room.isSessionEmpty()) {
                removeRoom(email);
            }
        }
    }

    public void sendMessageToAll(String email, ChatMessageDto message) {
        ChatRoomResDto room = findRoomById(email);
        if (room != null) {
            for (WebSocketSession session : room.getSessions()) {
                sendMessage(session, message);
            }
            saveMessage(message);
        }
    }
    public void saveMessage(ChatMessageDto messageDto) {
        ChatEntity chatEntity = ChatEntity.builder()
                .roomId(messageDto.getRoomId())
                .sender(messageDto.getSender())
                .chatData(messageDto.getMessage())
                .regDate(LocalDateTime.now())
                .build();
        ChatEntity savedEntity = chatRepository.save(chatEntity);
        log.info("Saved chat message:{}",savedEntity);
    }
    public List<ChatEntity> getChatMessagesByRoomId(String email) {
        return chatRepository.findByRoomIdOrderByRegDateAsc(email);
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}