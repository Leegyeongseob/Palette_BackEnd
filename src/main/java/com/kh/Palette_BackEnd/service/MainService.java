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
        Optional<CoupleEntity> coupleEntity = coupleRepository.findByCoupleName(coupleName);
        if(coupleEntity.isEmpty()){
            log.warn("Couple with name {} not found", coupleName);
            return new ArrayList<>(); // 빈 리스트 반환 혹은 예외 처리 로직 추가
        }
        Optional<MemberEntity> firstEmail = memberRepository.findByEmail(coupleEntity.get().getFirstEmail());
        Optional<MemberEntity> secondEmail = memberRepository.findByEmail(coupleEntity.get().getSecondEmail());
        List<String> list = new ArrayList<>();
        list.add(firstEmail.get().getNickName());
        list.add(secondEmail.get().getNickName());
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
