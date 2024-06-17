package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="GalleryPagePlusPayment_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryPagePlusPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="galleryPagePlusPayment_id")
    private Long id;
}
