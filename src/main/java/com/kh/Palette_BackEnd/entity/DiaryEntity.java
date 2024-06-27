package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Diary_TB")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="diary_id")
    private Long id;
    // 해당 날짜
    private LocalDate dDay;

    //기념일
    private LocalDate anniversary;

    // 해당 날짜의 상세내용.
    private String contents;

    //커플이 둘다 기념일을 볼 수 있어야 함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;


    // 다이어리 작성 계정.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email")
    private MemberEntity member;

}
