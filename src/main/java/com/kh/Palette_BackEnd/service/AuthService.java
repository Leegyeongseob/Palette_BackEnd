package com.kh.Palette_BackEnd.service;



import com.kh.Palette_BackEnd.dto.TokenDto;
import com.kh.Palette_BackEnd.dto.reqdto.CoupleReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.LoginReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.MemberReqDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.jwt.TokenProvider;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final CoupleRepository coupleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PersistenceContext
    EntityManager em;
    //회원가입
    public String signup(MemberReqDto requestDto) {
        try {
            MemberEntity member = requestDto.toMemberEntity(passwordEncoder);
            memberRepository.saveAndFlush(member);
            em.clear();
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

    // 로그인
    public TokenDto login(LoginReqDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }


    // 커플이름 중복확인
    public boolean coupleNameSearch(String coupleName) {
        return coupleRepository.existsByCoupleName(coupleName);
    }
    // 커플이름 Insert
    public String coupleNameInsert(CoupleReqDto requestDto){
        CoupleEntity coupleEntity;
        boolean isExist = coupleRepository.existsByCoupleName(requestDto.getCoupleName());
        try {
            if(isExist){
                coupleEntity = coupleRepository.findByCoupleName(requestDto.getCoupleName());
                coupleEntity.setSecondEmail(requestDto.getEmail());
            }
            else {
                coupleEntity = new CoupleEntity();
                coupleEntity.setCoupleName(requestDto.getCoupleName());
                coupleEntity.setFirstEmail(requestDto.getEmail());
            }
            coupleRepository.saveAndFlush(coupleEntity);
            em.clear();
            return "Success";
        }catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "커플등록 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "커플등록 중 오류가 발생했습니다.";
        }
    }
    // 커플이름 중복 시 짝 이메일을 확인하는 함수
    public String coupleEmailCheck(String coupleName){
        CoupleEntity coupleEntity = coupleRepository.findByCoupleName(coupleName);
        return coupleEntity.getFirstEmail();
    }
}
