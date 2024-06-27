package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.TokenDto;
import com.kh.Palette_BackEnd.dto.reqdto.LoginReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.MemberReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthController {
    private final AuthService authService;
    //아이디 중복 확인
    @PostMapping("/email")
    public ResponseEntity<Boolean> emailIsExist(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(authService.isExistEmail(email.get("email")));
    }
    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberReqDto requestDto){
        return ResponseEntity.ok(authService.signup(requestDto));
    }
    //로그인
    @PostMapping("login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginReqDto requestDto){
        return ResponseEntity.ok(authService.login(requestDto));
    }

}
