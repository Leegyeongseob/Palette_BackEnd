package com.kh.Palette_BackEnd.entity;


import com.kh.Palette_BackEnd.constant.Authority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="Member_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String pwd;
    private String name;
    private String nickName;
    private String coupleName;
    private String sex;
    private String profileImgUrl;
    @Column(length = 9)
    private boolean registrationNumber;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    //둘이서 채팅
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "chat_id")
    private ChatEntity chat;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;


    //작성자를 불러오기 위한 조인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="guestBook_id")
    private GuestBookEntity guestBook;


    //작성자를 불러오기 위한 조인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private BoardEntity board;


}
