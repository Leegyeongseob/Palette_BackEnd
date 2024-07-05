package com.kh.Palette_BackEnd.service;


import com.kh.Palette_BackEnd.dto.reqdto.DiaryReqDto;
import com.kh.Palette_BackEnd.entity.DiaryCheckListEntity;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.DiaryCheckListRepository;
import com.kh.Palette_BackEnd.repository.DiaryRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor

public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DiaryCheckListRepository diaryCheckListRepository;

    @Transactional
    public DiaryEntity saveDiary(DiaryReqDto diaryReqDto) {
        try {
            // DiaryEntity 생성
            DiaryEntity diaryEntity = DiaryEntity.builder()
                    .email(diaryReqDto.getEmail())
                    .anniversary(diaryReqDto.getAnniversary())
                    .dateContents(diaryReqDto.getDateContents())
                    .contents(diaryReqDto.getContents())
                    .build();

            // Diary 저장
            DiaryEntity savedDiary = diaryRepository.save(diaryEntity);

            // DiaryCheckListEntity 생성 및 저장
            DiaryCheckListEntity diaryCheckListEntity = new DiaryCheckListEntity();
            diaryCheckListEntity.setEvents(diaryReqDto.getEvents());
            diaryCheckListEntity.setDiary(savedDiary);
            diaryCheckListRepository.save(diaryCheckListEntity);

            return savedDiary;
        } catch (Exception e) {
            log.error("Failed to save diary: {}", e.getMessage());
            throw new RuntimeException("Failed to save diary", e);
        }
    }
}