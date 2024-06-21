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
    private LocalDate dDay;
    private LocalDate anniversary;
    private String contents;

    //커플이 둘다 기념일을 볼 수 있어야 함.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;

}
