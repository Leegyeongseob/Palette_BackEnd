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
    private String coupleCode;
    private String sex;
    @Column(length = 9)
    private int registrationNumber;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "chat_id")
    private ChatEntity chat;
}
