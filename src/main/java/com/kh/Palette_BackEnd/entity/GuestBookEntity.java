package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name="GuestBook_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="guestBook_id")
    private Long id;

    // 작성 날짜, 시간
    private LocalDateTime regDate;
    // 내용
    private String contents;

    // 방명록이 속한 커플
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;

    //작성자를 불러오기 위한 조인
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="member_id")
    private MemberEntity member;


    // DB에 값을 저장할때 시간 값 저장.
    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
    }
}
