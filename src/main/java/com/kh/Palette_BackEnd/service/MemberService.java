package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.dto.reqdto.MemberUpdateReqDto;
import com.kh.Palette_BackEnd.dto.resdto.MemberResDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final CoupleRepository coupleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

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
                member.setPwd(passwordEncoder.encode(memberUpdateReqDto.getPwd()));
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
    // 커플이름 search
    public String coupleNameSearch(String email){
        Optional<CoupleEntity> coupleEntity = coupleRepository.findByFirstEmailOrSecondEmail(email,email);
        if(coupleEntity.isPresent()){
            return coupleEntity.get().getCoupleName();
        }
        else {
            // 커플을 찾지 못한 경우 예외를 던집니다.
            throw new EntityNotFoundException("해당 이메일로 커플을 찾을 수 없습니다: " + email);
        }
    }
    // 커플 이름으로 솔로인지 커플인지 확인
    public boolean isCoupleTrue(String coupleName) {
        Optional<CoupleEntity> coupleEntity = coupleRepository.findByCoupleName(coupleName);

        if (coupleEntity.isPresent()) {
            CoupleEntity entity = coupleEntity.get();
            String firstEmail = entity.getFirstEmail();
            String secondEmail = entity.getSecondEmail();

            return firstEmail != null && !firstEmail.isEmpty() && secondEmail != null && !secondEmail.isEmpty();
        } else {
            throw new EntityNotFoundException("해당 커플 이름으로 정보를 찾을 수 없습니다: " + coupleName);
        }
    }
    //프로필url 저장 Axios
    public boolean profileUrlSave(String email,String url){
        Optional<MemberEntity> memberEntityOpt = memberRepository.findByEmail(email);
        if(memberEntityOpt.isPresent()){
            MemberEntity member = memberEntityOpt.get();
            member.setProfileImgUrl(url);
            memberRepository.saveAndFlush(member);
            return true;
        }
        else{
            return false;
        }
    }
    // 커플 프로필 url을 가져오는 Axios
    public List<String> coupleProfileUrl(String email) {
        List<String> list = new ArrayList<>();

        // 첫 번째 이메일로 커플 검색
        Optional<CoupleEntity> coupleEntityOpt1 = coupleRepository.findByFirstEmail(email);
        if (coupleEntityOpt1.isPresent()) {
            CoupleEntity couple = coupleEntityOpt1.get();
            // 첫 번째 이메일이 존재하고 두 번째 이메일도 존재할 경우
            if (couple.getSecondEmail() != null) {
                Optional<MemberEntity> memberEntity1 = memberRepository.findByEmail(couple.getFirstEmail());
                Optional<MemberEntity> memberEntity2 = memberRepository.findByEmail(couple.getSecondEmail());
                list.add(memberEntity1.get().getProfileImgUrl());
                list.add(memberEntity2.get().getProfileImgUrl());
            } else {
                // 첫 번째 이메일만 존재할 경우
                Optional<MemberEntity> memberEntity1 = memberRepository.findByEmail(couple.getFirstEmail());
                list.add(memberEntity1.get().getProfileImgUrl());
            }
            return list;
        }

        // 두 번째 이메일로 커플 검색
        Optional<CoupleEntity> coupleEntityOpt2 = coupleRepository.findBySecondEmail(email);
        if (coupleEntityOpt2.isPresent()) {
            CoupleEntity couple = coupleEntityOpt2.get();
            // 두 번째 이메일이 존재하고 첫 번째 이메일도 존재할 경우
            if (couple.getFirstEmail() != null) {
                Optional<MemberEntity> memberEntity1 = memberRepository.findByEmail(couple.getFirstEmail());
                Optional<MemberEntity> memberEntity2 = memberRepository.findByEmail(couple.getSecondEmail());
                list.add(memberEntity2.get().getProfileImgUrl());
                list.add(memberEntity1.get().getProfileImgUrl());
            } else {
                // 두 번째 이메일만 존재할 경우
                Optional<MemberEntity> memberEntity2 = memberRepository.findByEmail(couple.getSecondEmail());
                list.add(memberEntity2.get().getProfileImgUrl());
            }
            return list;
        }

        // 커플 정보가 없을 경우 빈 리스트 반환
        return list;
    }
}
