package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.entity.*;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.GalleryListRepository;
import com.kh.Palette_BackEnd.repository.GalleryRepository;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import com.kh.Palette_BackEnd.resdto.GalleryResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final GalleryListRepository galleryListRepository;

    @Autowired
    private final CoupleRepository coupleRepository;

    @Transactional
    public GalleryEntity saveGallery(GalleryReqDto galleryReqDto) {
        try {
            Optional<CoupleEntity> coupleOpt = coupleRepository.findByFirstEmailOrSecondEmail(galleryReqDto.getEmail(), galleryReqDto.getEmail());
            CoupleEntity couple = coupleOpt.orElseThrow(() -> new RuntimeException("Couple not found"));

            GalleryEntity galleryEntity = GalleryEntity.builder()
                    .email(galleryReqDto.getEmail())
                    .couple(couple)
                    .build();
            galleryEntity = galleryRepository.save(galleryEntity);

            for (String url : galleryReqDto.getUrls()) {
                GalleryListEntity galleryListEntity = new GalleryListEntity();
                galleryListEntity.setImgUrl(url);
                galleryListEntity.setGallery(galleryEntity);
                galleryListRepository.save(galleryListEntity);
            }
            return galleryEntity;
        } catch (Exception e) {
            log.error("Failed to save diary: {}", e.getMessage());
            throw new RuntimeException("Failed to save gallery", e);
        }
    }

    // 갤러리 조회
    @Transactional
    public List<GalleryResDto> getImagesByEmail(String email) {
        CoupleEntity couple = coupleRepository.findByFirstEmailOrSecondEmail(email, email)
                .orElseThrow(() -> new RuntimeException("Couple not found"));

        List<GalleryEntity> galleries = galleryRepository.findByCouple(couple);

        return galleries.stream().map(gallery -> {
            List<String> urls = galleryListRepository.findByGallery(gallery)
                    .stream()
                    .map(GalleryListEntity::getImgUrl)
                    .collect(Collectors.toList());

            return GalleryResDto.builder()
                    .email(gallery.getEmail())
                    .urls(urls)
                    .build();
        }).collect(Collectors.toList());
    }




}