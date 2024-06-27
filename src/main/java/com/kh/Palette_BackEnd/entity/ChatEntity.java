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
    private int isRead;
    private LocalDateTime regDate;

    @Column(name="room_id")
    private String roomId;

    @Column(name="sender")
    private String sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity member;

    // DB에 값을 저장할때 시간 값 저장.
    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();


    }

}
