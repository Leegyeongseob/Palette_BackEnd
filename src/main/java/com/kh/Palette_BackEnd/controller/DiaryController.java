package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.DiaryReqDto;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import com.kh.Palette_BackEnd.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/save")
    public ResponseEntity<DiaryResDto> savediary(@RequestBody DiaryReqDto requestDto) {
        return ResponseEntity.ok(diaryService.savediary(requestDto));
    }
}
