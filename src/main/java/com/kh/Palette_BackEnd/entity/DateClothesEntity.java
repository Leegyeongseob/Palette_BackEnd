package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="DateClothes_TB")
public class DateClothesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="dateClothes_id")
    private Long id;
}
