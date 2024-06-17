package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="BoardList_TB")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="boardList_id")
    private Long id;

}
