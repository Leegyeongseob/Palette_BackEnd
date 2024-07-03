package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.dto.reqdto.MemberReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class MemberService {
    private final CoupleRepository coupleRepository;
    private final MemberRepository memberRepository;

    public MemberResDto memberAxios(String email){
        Optional<MemberEntity> memberEntity = memberRepository.findByEmail(email);
        MemberEntity member = memberEntity.get();
        return MemberResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickName(member.getNickName())
                .coupleName(member.getCouple().getCoupleName())
                .build();
    }
    //회원 정보 수정 (커플 테이블도 수정해야함.)
    public String memberModify(MemberReqDto memberReqDto) {
        try {
            // 회원 정보 조회
            MemberEntity memberEntity = memberRepository.findByEmail(memberReqDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

                        // 커플 정보 조회 및 업데이트
                        Optional<CoupleEntity> coupleEntity1 = coupleRepository.findByFirstEmail(memberEntity.getEmail());
                Optional<CoupleEntity> coupleEntity2 = coupleRepository.findBySecondEmail(memberEntity.getEmail());
                if (coupleEntity1.isPresent()) {
                    coupleEntity1.get().setFirstEmail(memberReqDto.getEmail());
                    coupleEntity1.get().setCoupleName(memberReqDto.getCoupleName());
                    coupleRepository.save(coupleEntity1.get());
                }
                else{
                    coupleEntity2.get().setSecondEmail(memberReqDto.getEmail());
                coupleEntity2.get().setCoupleName(memberReqDto.getCoupleName());
                coupleRepository.save(coupleEntity2.get());
            }
            // 회원 정보 업데이트
            memberEntity.setEmail(memberReqDto.getEmail());
            memberEntity.setName(memberReqDto.getName());
            memberEntity.setNickName(memberReqDto.getNickName());
            // 회원 정보 저장
            memberRepository.save(memberEntity);

            return "Success";
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "회원 정보 수정 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "회원 정보 수정 중 오류가 발생했습니다.";
        }
    }
    //회원정보삭제 (커플테이블도 둘다 없을때 삭제해야함.)
    public String memberDelete(String email) {
        try {
            // 회원 정보 조회
            MemberEntity memberEntity = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

            // 커플 정보 조회
            CoupleEntity coupleEntity = coupleRepository.findByFirstEmailOrSecondEmail(email, email);

            if (coupleEntity == null) {
                // 커플 정보가 없으면 회원 정보만 삭제
                memberRepository.delete(memberEntity);
                return "회원 정보가 삭제되었습니다.";
            } else {
                // 커플 정보가 있는 경우
                if (email.equals(coupleEntity.getFirstEmail())) {
                    // 현재 이메일이 첫 번째 이메일인 경우
                    coupleEntity.setFirstEmail(null);
                } else if (email.equals(coupleEntity.getSecondEmail())) {
                    // 현재 이메일이 두 번째 이메일인 경우
                    coupleEntity.setSecondEmail(null);
                }
                // 두 이메일 모두 null인 경우 커플 정보 삭제
                if (coupleEntity.getFirstEmail() == null && coupleEntity.getSecondEmail() == null) {
                    coupleRepository.delete(coupleEntity);
                }
                // 회원 정보 삭제
                memberRepository.delete(memberEntity);

                return "회원 정보 및 관련 커플 정보가 삭제되었습니다.";
            }
        } catch (Exception e) {
            return "회원 정보 삭제 중 오류가 발생했습니다.: " + e.getMessage();
        }
    }
}
