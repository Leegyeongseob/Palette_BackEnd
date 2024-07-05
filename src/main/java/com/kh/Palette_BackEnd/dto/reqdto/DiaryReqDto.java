package com.kh.Palette_BackEnd.dto.reqdto;

import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryReqDto {
    private String email;
    private LocalDate anniversary;
    private String dateContents;
    private String contents;
    private List<DiaryCheckListEntity.Event> events;
}
