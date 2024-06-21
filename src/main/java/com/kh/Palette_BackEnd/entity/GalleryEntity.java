package com.kh.Palette_BackEnd.entity;


import com.kh.Palette_BackEnd.constant.PagePlusSellStatus;
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

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private PagePlusSellStatus pagePlusSellStatus;

    private int openPage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;
}
