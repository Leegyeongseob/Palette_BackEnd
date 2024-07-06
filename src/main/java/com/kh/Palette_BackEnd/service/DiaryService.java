package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.dto.reqdto.DiaryReqDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.DiaryCheckListRepository;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryCheckListRepository diaryCheckListRepository;

    @Autowired
    private CoupleRepository coupleRepository;

    @Transactional
    public DiaryEntity saveDiary(DiaryReqDto diaryReqDto) {
        try {
            // CoupleEntity 조회
            Optional<CoupleEntity> coupleOpt = coupleRepository.findByFirstEmailOrSecondEmail(diaryReqDto.getEmail(), diaryReqDto.getEmail());
            CoupleEntity couple = coupleOpt.orElseThrow(() -> new RuntimeException("Couple not found"));

            // 날짜 파싱 및 변환
            LocalDate anniversary = diaryReqDto.getAnniversary();


            // 동일 날짜의 DiaryEntity 조회
            Optional<DiaryEntity> existingDiaryOpt = diaryRepository.findByCoupleAndAnniversary(couple, anniversary);

            DiaryEntity diaryEntity;
            if (existingDiaryOpt.isPresent()) {
                // 기존 데이터 업데이트
                diaryEntity = existingDiaryOpt.get();
                diaryEntity.setDateContents(diaryReqDto.getDateContents());
                diaryEntity.setContents(diaryReqDto.getContents());

                // 기존 DiaryCheckListEntity도 업데이트
                Optional<DiaryCheckListEntity> existingCheckListOpt = diaryCheckListRepository.findByDiary(diaryEntity);
                if (existingCheckListOpt.isPresent()) {
                    DiaryCheckListEntity diaryCheckListEntity = existingCheckListOpt.get();
                    diaryCheckListEntity.setEvents(diaryReqDto.getEvents());
                    diaryCheckListRepository.save(diaryCheckListEntity);
                } else {
                    DiaryCheckListEntity diaryCheckListEntity = new DiaryCheckListEntity();
                    diaryCheckListEntity.setEvents(diaryReqDto.getEvents());
                    diaryCheckListEntity.setDiary(diaryEntity);
                    diaryCheckListRepository.save(diaryCheckListEntity);
                }
            } else {
                // 새로운 DiaryEntity 생성
                diaryEntity = DiaryEntity.builder()
                        .email(diaryReqDto.getEmail())
                        .anniversary(anniversary)
                        .dateContents(diaryReqDto.getDateContents())
                        .contents(diaryReqDto.getContents())
                        .couple(couple)
                        .build();

                // Diary 저장
                diaryEntity = diaryRepository.save(diaryEntity);

                // 새로운 DiaryCheckListEntity 생성 및 저장
                DiaryCheckListEntity diaryCheckListEntity = new DiaryCheckListEntity();
                diaryCheckListEntity.setEvents(diaryReqDto.getEvents());
                diaryCheckListEntity.setDiary(diaryEntity);
                diaryCheckListRepository.save(diaryCheckListEntity);
            }

            return diaryEntity;
        } catch (Exception e) {
            log.error("Failed to save diary: {}", e.getMessage());
            throw new RuntimeException("Failed to save diary", e);
        }
    }

    @Transactional
    public List<DiaryResDto> getDiariesByEmail(String email) {
        log.debug("Fetching couple by email: {}", email);
        CoupleEntity couple = coupleRepository.findByFirstEmailOrSecondEmail(email, email)
                .orElseThrow(() -> new RuntimeException("Couple not found"));

        log.debug("Found couple: {}", couple);
        List<DiaryEntity> diaries = diaryRepository.findByCouple(couple);

        log.debug("Found diaries: {}", diaries);
        return diaries.stream().map(diary -> {
            List<DiaryCheckListEntity.Event> events = diaryCheckListRepository.findByDiary(diary)
                    .map(DiaryCheckListEntity::getEvents).orElse(null);

            log.debug("Found events: {}", events);
            return DiaryResDto.builder()
                    .email(diary.getEmail())
                    .anniversary(diary.getAnniversary())
                    .dateContents(diary.getDateContents())
                    .contents(diary.getContents())
                    .events(events)
                    .build();
        }).collect(Collectors.toList());
    }
}