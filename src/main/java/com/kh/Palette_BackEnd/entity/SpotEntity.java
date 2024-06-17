package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Spot_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="spot_id")
    private Long id;
    private String detailRegion;
    private String category;
    private String imgUrl;
    private String title;
    private String context;
}
