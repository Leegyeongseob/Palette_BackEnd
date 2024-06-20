package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Couple_TB")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoupleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="couple_id")
    private Long id;
    // 커플 이메일(아이디)
    private String firstEmail;
    private String secondEmail;
    @Column(unique = true)
    private String coupleName;
}
