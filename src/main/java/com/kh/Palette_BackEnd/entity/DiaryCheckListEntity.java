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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id")
    private DiaryEntity diaryCheck;
}
