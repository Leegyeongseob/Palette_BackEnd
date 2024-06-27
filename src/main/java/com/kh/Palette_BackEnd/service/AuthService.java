package com.kh.Palette_BackEnd.service;



import com.kh.Palette_BackEnd.dto.TokenDto;
import com.kh.Palette_BackEnd.dto.reqdto.LoginReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.MemberReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.jwt.TokenProvider;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    //회원가입
    public String signup(MemberReqDto requestDto) {
        try {
            MemberEntity member = requestDto.toMemberEntity(passwordEncoder);
            memberRepository.save(member);
            return "Success";
        }catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "회원가입 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "회원가입 중 오류가 발생했습니다.";
        }
    }
    // 이메일 중복확인
    public boolean isExistEmail(String Email){
        return memberRepository.existsByEmail(Email);
    }

    public TokenDto login(LoginReqDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

}
