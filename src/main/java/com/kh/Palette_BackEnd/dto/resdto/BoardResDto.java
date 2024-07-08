package com.kh.Palette_BackEnd.dto.resdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResDto {
    private Long id;
    private String title;
    private LocalDateTime regDate;
    private String imgUrl;
    private String contents;
    private String memberEmail;
    private Long boardListId;
}