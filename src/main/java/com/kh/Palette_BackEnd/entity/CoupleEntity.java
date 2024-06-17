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
    private Long coupleId;
    private String firstEmail;
    private String secondEmail;
    @Column(unique = true)
    private String coupleName;
}
