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

    @Enumerated(EnumType.STRING)
    private PagePlusSellStatus pagePlusSellStatus;

    private int openPage;


    // 커플모두 볼수 있어야함. 저장 데이터 불러오기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="couple_id")
    private CoupleEntity couple;

    // 사진 올리는 계정 조인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="email")
    private MemberEntity member;
}
