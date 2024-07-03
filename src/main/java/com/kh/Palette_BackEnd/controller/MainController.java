package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MainService mainService;
    //커플이름으로 닉네임 찾기
    @GetMapping("/searchNickName")
    public ResponseEntity<List<String>> searchNickName(@RequestParam String coupleName){
        return ResponseEntity.ok(mainService.searchNickName(coupleName));
    }
    //커플이름으로 Dday존재 확인
    @GetMapping("/isExistDday")
    public ResponseEntity<Boolean> isExistDday(@RequestParam String coupleName){
        return ResponseEntity.ok(mainService.isExistDday(coupleName));
    }

}
