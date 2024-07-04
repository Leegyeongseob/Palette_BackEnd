package com.kh.Palette_BackEnd.dto.reqdto;

import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@CrossOrigin("http://localhost:3000")
public class DiaryReqDto {
    private LocalDate anniversary;
    private String dateContents;
    private String contents;
    // MemberReqDto -> Member
    public DiaryEntity toEntity(DiaryRepository diaryRepository) {
        return DiaryEntity.builder()
                .dateContents(dateContents)
                .contents(contents)
                .anniversary(anniversary)
                .build();
    }
}
