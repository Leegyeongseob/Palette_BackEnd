package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "DateCourse_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateCourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="dateCourse_id")
    private Long id;

    // 데이트 코스명
    private String Title;
    // 코스 장소들
    private String place;
    // 코스 메모
    private String memo;
    // 저장 날짜
    private LocalDate date;

    // 커플이 둘다 저장된 코스를 볼 수 있어야 함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;


    @PrePersist
    public void prePersist(){
        date = LocalDate.now();
    }
}
