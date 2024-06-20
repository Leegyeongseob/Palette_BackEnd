package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Chat_TB")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_id")
    private Long id;

    private String chatData;

    private String imgUrl;

    private String emojiUrl;

    private LocalDateTime regDate;
    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
    }
}
