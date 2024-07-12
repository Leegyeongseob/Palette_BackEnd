package com.kh.Palette_BackEnd.dto.resdto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBookResDto {
    private Long id;

    private String title;

    private LocalDateTime regDateTime;

    private String contents;

    private String memberEmail;

    private String coupleName;
}
