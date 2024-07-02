package com.kh.Palette_BackEnd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    public enum MessageType {
        ENTER, TALK, CLOSE
    }

    private MessageType type;
    private String sender;
    private String receiver; // 추가된 필드
    private String message;
}
