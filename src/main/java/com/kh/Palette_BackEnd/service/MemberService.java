package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.dto.reqdto.MemberReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final CoupleRepository coupleRepository;
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;
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
    public String memberModify(MemberUpdateReqDto memberUpdateReqDto) {
        try {
            //수정전 이메일로 정보 조회
            Optional<MemberEntity> memberEntity = memberRepository.findByEmail(memberUpdateReqDto.getEmail());
            if(memberEntity.isPresent())
            {
                MemberEntity member = memberEntity.get();
                member.setEmail(memberUpdateReqDto.getUpdateEmail());
                member.setName(memberUpdateReqDto.getName());
                member.setNickName(memberUpdateReqDto.getNickName());
                member.getCouple().setCoupleName(memberUpdateReqDto.getCoupleName());
                Optional<CoupleEntity> coupleEntity1 = coupleRepository.findByFirstEmail(memberUpdateReqDto.getEmail());
                Optional<CoupleEntity> coupleEntity2 = coupleRepository.findBySecondEmail(memberUpdateReqDto.getEmail());
                if(coupleEntity1.isPresent()){
                    coupleEntity1.get().setFirstEmail(memberUpdateReqDto.getUpdateEmail());
                    coupleEntity1.get().setCoupleName(memberUpdateReqDto.getCoupleName());
                    coupleRepository.saveAndFlush(coupleEntity1.get());
                    em.clear();
                } else if (coupleEntity2.isPresent()) {
                    coupleEntity2.get().setSecondEmail(memberUpdateReqDto.getUpdateEmail());
                    coupleEntity2.get().setCoupleName(memberUpdateReqDto.getCoupleName());
                    coupleRepository.saveAndFlush(coupleEntity2.get());
                    em.clear();
                }
                memberRepository.saveAndFlush(member);
                em.clear();
            }
            return "Success";
        } catch (DataAccessException e) {
            // 데이터 접근 예외 처리 (예: 데이터베이스 접근 오류)
            return "회원 정보 수정 실패: 데이터베이스 접근 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외의 예외 처리
            return "회원 정보 수정 중 오류가 발생했습니다.";
        }
    }
    // 회원정보삭제 (커플테이블도 둘 다 없을 때 삭제해야 함.)
    public String memberDelete(String email) {
        try {
            // 회원 정보 조회
            MemberEntity memberEntity = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

            // 회원 정보 삭제
            memberRepository.delete(memberEntity);

            // 커플 정보 조회
            Optional<CoupleEntity> coupleEntity1 = coupleRepository.findByFirstEmail(email);
            Optional<CoupleEntity> coupleEntity2 = coupleRepository.findBySecondEmail(email);

            // 커플 정보 처리
            if (coupleEntity1.isPresent()) {
                CoupleEntity couple = coupleEntity1.get();
                if (couple.getSecondEmail() == null || couple.getSecondEmail().isEmpty()) {
                    coupleRepository.delete(couple);
                } else {
                    couple.setFirstEmail(null);
                    coupleRepository.saveAndFlush(couple);
                    em.clear();
                }
            } else if (coupleEntity2.isPresent()) {
                CoupleEntity couple = coupleEntity2.get();
                if (couple.getFirstEmail() == null || couple.getFirstEmail().isEmpty()) {
                    coupleRepository.delete(couple);
                } else {
                    couple.setSecondEmail(null);
                    coupleRepository.saveAndFlush(couple);
                    em.clear();
                }
            }

            return "회원 정보 및 관련 커플 정보가 삭제되었습니다.";
        } catch (Exception e) {
            return "회원 정보 삭제 중 오류가 발생했습니다.: " + e.getMessage();
        }
    }

}
