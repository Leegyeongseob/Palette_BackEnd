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
import java.util.Optional;

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
            //Dto to Entity
            MemberEntity member = requestDto.toMemberEntity(passwordEncoder);
            //coupleName을 찾아서 추가
            Optional<CoupleEntity> coupleEntity = coupleRepository.findByCoupleName(requestDto.getCoupleName());
            // 조인부분에 추가
            coupleEntity.ifPresent(member::setCouple);
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
    public String coupleNameInsert(CoupleReqDto requestDto)
    {
        try{
        CoupleEntity coupleEntity = new CoupleEntity();
        coupleEntity.setCoupleName(requestDto.getCoupleName());
        coupleEntity.setFirstEmail(requestDto.getEmail());
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
    public String coupleEmailCheck(String coupleName) {
        Optional<CoupleEntity> coupleEntity = coupleRepository.findByCoupleName(coupleName);
        return coupleEntity.map(CoupleEntity::getFirstEmail).orElse(null);
    }
    // 커플 테이블에 계정 추가
    public String secondCoupleNameInsert(CoupleReqDto coupleReqDto) {
        try {
            Optional<CoupleEntity> coupleEntity = coupleRepository.findByCoupleName(coupleReqDto.getCoupleName());
            if (coupleEntity.isPresent()) {
                CoupleEntity entity = coupleEntity.get();
                entity.setSecondEmail(coupleReqDto.getEmail());
                coupleRepository.saveAndFlush(entity);
                em.clear();
                return "Success";
            } else {
                return "계정추가 실패: 해당 이름의 커플이 존재하지 않습니다.";
            }
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "계정추가 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "계정추가 중 오류가 발생했습니다.";
        }
    }
    // email로 커플이름 search
    public String emailToCoupleNameSearch(String email){
        try {
            Optional<MemberEntity> memberEntity = memberRepository.findByEmail(email);
            if (memberEntity.isPresent()) {
                MemberEntity member = memberEntity.get();
                log.info(member.getCouple().getCoupleName());
                return member.getCouple().getCoupleName();
            } else {
                return "계정이 존재하지 않습니다.";
            }
        }catch (DataAccessException e) {
                // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
                return "커플이름 search 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
            } catch (Exception e) {
                // 그 외의 예외 처리
                return "커플이름 search 중 오류가 발생했습니다.";
            }
        }
    }

