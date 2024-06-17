package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

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

}
