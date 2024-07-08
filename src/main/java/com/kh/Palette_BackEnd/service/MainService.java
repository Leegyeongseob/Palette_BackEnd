package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {
    private final CoupleRepository coupleRepository;
    private final MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;
    // 커플이름으로 닉네임 불러오기
    public List<String> searchNickName(String email, String coupleName) {
        List<String> nickNames = new ArrayList<>();

        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);

        if (coupleEntityOpt.isPresent()) {
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            String firstEmail = coupleEntity.getFirstEmail();
            String secondEmail = coupleEntity.getSecondEmail();

            // firstEmail만 값이 존재하는 경우 처리
            if (firstEmail != null &&secondEmail ==null && firstEmail.equals(email)) {
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }

            // secondEmail만 값이 존재하는 경우 처리
            if (secondEmail != null && firstEmail == null && secondEmail.equals(email)) {
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }
            // 값이 둘다 존재하고 firstEmail이 email과 같은 경우
            if (firstEmail != null && secondEmail != null && firstEmail.equals(email)){
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }
            // 값이 둘다 존재하고 secondEmail이 email과 같은 경우
            if (firstEmail != null && secondEmail != null && secondEmail.equals(email)){
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));

            }
        }
        return nickNames;
    }
    // 커플이름으로 D-day 찾기
    public String searchDday(String coupleName) {
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);
        if (coupleEntityOpt.isPresent()) {
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            String datingDay = coupleEntity.getDDay();
            if (datingDay != null) {
                return datingDay;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("해당 커플 이름으로 정보를 찾을 수 없습니다: " + coupleName);
        }
    }
    // D-day 저장
    public boolean saveDday(String coupleName,String datingDay){
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);
        if(coupleEntityOpt.isPresent()){
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            coupleEntity.setDDay(datingDay);
            coupleRepository.saveAndFlush(coupleEntity);
            em.clear();
            return true;
        }
        else{
            return false;
        }
    }
}
