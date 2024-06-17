package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="GalleryThemePayment_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryThemePaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="galleryThemePayment_id")
    private Long id;

}
