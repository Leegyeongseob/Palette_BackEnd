package com.kh.Palette_BackEnd.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Board_TB")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="board_id")
    private Long id;
}
