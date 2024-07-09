package com.kh.Palette_BackEnd.controller;


import com.kh.Palette_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    //커플이름 뽑아오기
    @PostMapping("/coupleNameSearch")
    public ResponseEntity<String> coupleNameSearch(@RequestBody Map<String,String> email){
        return ResponseEntity.ok(memberService.coupleNameSearch(email.get("email")));
    }
    //커플이름으로
    @PostMapping("/isCoupleTrue")
    public ResponseEntity<Boolean> isCoupleTrue(@RequestBody Map<String,String> coupleName){
        return ResponseEntity.ok(memberService.isCoupleTrue(coupleName.get("coupleName")));
    }
    //프로필url 저장 Axios
    @GetMapping("/profileUrlSave")
    public ResponseEntity<Boolean> profileUrlSave(@RequestParam String email,@RequestParam String url){
        return ResponseEntity.ok(memberService.profileUrlSave(email,url));
    }
    //커플 프로필 url을 가져오는 Axios
    @GetMapping("coupleProfileUrl")
    public ResponseEntity<List<String>> coupleProfileUrl(@RequestParam String email){
        return ResponseEntity.ok(memberService.coupleProfileUrl(email));
    }
}
