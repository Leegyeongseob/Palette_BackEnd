package com.kh.Palette_BackEnd.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="SpotChoice_TB")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpotChoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="spotChoice_id")
    private Long id;


}
