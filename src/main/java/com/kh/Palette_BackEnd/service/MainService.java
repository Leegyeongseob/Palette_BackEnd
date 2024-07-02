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
    public List<String> searchNickName(String coupleName){
        // 커플 엔터티를 커플 이름으로 찾기
        Optional<CoupleEntity> coupleEntity = coupleRepository.findByCoupleName(coupleName);
        if (coupleEntity.isEmpty()) {
            log.warn("Couple with name {} not found", coupleName);
            return new ArrayList<>(); // 빈 리스트 반환 혹은 예외 처리 로직 추가
        }

        // 첫 번째 이메일로 멤버 엔터티 찾기
        Optional<MemberEntity> firstEmail = memberRepository.findByEmail(coupleEntity.get().getFirstEmail());
        if (firstEmail.isEmpty()) {
            log.warn("First email {} for couple {} not found", coupleEntity.get().getFirstEmail(), coupleName);
            return new ArrayList<>(); // 빈 리스트 반환 혹은 예외 처리 로직 추가
        }

        // 두 번째 이메일로 멤버 엔터티 찾기
        String secondEmailStr = coupleEntity.get().getSecondEmail();
        Optional<MemberEntity> secondEmail = Optional.empty();
        if (secondEmailStr != null) {
            secondEmail = memberRepository.findByEmail(secondEmailStr);
            if (secondEmail.isEmpty()) {
                log.warn("Second email {} for couple {} not found", secondEmailStr, coupleName);
            }
        }

        List<String> list = new ArrayList<>();
        list.add(firstEmail.get().getNickName());
        if (secondEmail.isPresent()) {
            list.add(secondEmail.get().getNickName());
        } else {
            log.info("Second email is null or not found for couple {}", coupleName);
        }

        return list;
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
