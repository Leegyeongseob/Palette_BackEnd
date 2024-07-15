package com.kh.Palette_BackEnd.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "Chat_Room")
public class ChatRoomEntity {
    @Id
    @Column(name = "room_id")
    private String roomId;
    @Column(name = "Created_At")
    private LocalDateTime createdAt;
}
