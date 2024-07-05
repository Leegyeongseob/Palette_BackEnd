package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    // 정보 불러오기
    @PostMapping("/info")
    public ResponseEntity<MemberResDto> memberAxios(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(memberService.memberAxios(email.get("email")));
    }
    //회원 수정
    @PostMapping("/modify")
    public ResponseEntity<String> memberModify(@RequestBody MemberUpdateReqDto memberUpdateReqDto){
        return ResponseEntity.ok(memberService.memberModify(memberUpdateReqDto));
    }
    //회원 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> memberDelete(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(memberService.memberDelete(email.get("email")));
    }
}
