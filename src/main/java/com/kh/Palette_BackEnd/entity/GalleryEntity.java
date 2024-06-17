package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Gallery_TB")
public class GalleryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="gallery_id")
    private Long id;
}
