package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="DiaryCheckList_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryCheckListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="diaryCheckList_id")
    private Long id;
    // 체크리스트 내용
    private String contents;


    // 계정 다이어리 하나에 안에 여러 체크 리스트 값 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id")
    private DiaryEntity diaryCheck;
}
