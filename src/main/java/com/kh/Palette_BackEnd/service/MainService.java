package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    // 커플이름으로 닉네임 불러오기
    public List<String> searchNickName(String email, String coupleName) {
        List<String> nickNames = new ArrayList<>();

        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);

        if (coupleEntityOpt.isPresent()) {
            CoupleEntity coupleEntity = coupleEntityOpt.get();
            String firstEmail = coupleEntity.getFirstEmail();
            String secondEmail = coupleEntity.getSecondEmail();

            if (firstEmail.equals(email)) {
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }
            if (secondEmail.equals(email)) {
                Optional<MemberEntity> memberSecondEntityOpt = memberRepository.findByEmail(secondEmail);
                Optional<MemberEntity> memberFirstEntityOpt = memberRepository.findByEmail(firstEmail);
                memberSecondEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
                memberFirstEntityOpt.ifPresent(memberEntity -> nickNames.add(memberEntity.getNickName()));
            }
        }
        return nickNames;
    }
    // 커플이름으로 Dday 존재 확인
    public boolean isExistDday(String coupleName){
       Optional<CoupleEntity> coupleEntityOpt  = coupleRepository.findByCoupleName(coupleName);
       if(coupleEntityOpt.isPresent()){
           CoupleEntity coupleEntity = coupleEntityOpt.get();
           return coupleEntity.getDatingDay() !=null;
       }
       return false;
    }
}
