package com.kh.Palette_BackEnd.resdto;

import com.kh.Palette_BackEnd.entity.DiaryEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryResDto {

    private LocalDate anniversary;
    private String dateContents;
    private String contents;

    // Member -> MemberResDto
    public static DiaryResDto of(DiaryEntity diaryEntity) {
        return DiaryResDto.builder()
                .dateContents(diaryEntity.getDateContents())
                .contents(diaryEntity.getContents())
                .anniversary(diaryEntity.getAnniversary())
                .build();
    }
}
