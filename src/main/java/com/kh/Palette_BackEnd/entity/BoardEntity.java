package com.kh.Palette_BackEnd.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="Board_TB")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="board_id")
    private Long id;

    private String title;

    private LocalDate regDate;

    private String imgUrl;

    private String contents;

    //작성자를 불러오기 위한 조인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="boardList_id")
    private BoardListEntity boardList;

    // DB에 값을 저장할 때 시간 값 저장.
    @PrePersist
    public void prePersist(){
        regDate = LocalDate.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", nullable = false)
    private CoupleEntity couple;
}
