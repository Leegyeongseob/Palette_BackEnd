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

    private String Title;
    // 작성 날짜, 시간
    private LocalDateTime RegDate;
    // 내용
    private String contents;



    // DB에 값을 저장할때 시간 값 저장.
    @PrePersist
    public void prePersist(){
        RegDate = LocalDateTime.now();
    }
}
