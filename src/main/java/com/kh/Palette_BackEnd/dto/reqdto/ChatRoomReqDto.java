package com.kh.Palette_BackEnd.dto.reqdto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 채팅방 개설 요청
public class ChatRoomReqDto {
    private String email;
    private String name;
}
