package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.DiaryReqDto;
import com.kh.Palette_BackEnd.dto.reqdto.GalleryReqDto;
import com.kh.Palette_BackEnd.entity.DiaryEntity;
import com.kh.Palette_BackEnd.entity.GalleryEntity;
import com.kh.Palette_BackEnd.resdto.DiaryResDto;
import com.kh.Palette_BackEnd.resdto.GalleryResDto;
import com.kh.Palette_BackEnd.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping("/save")
    public ResponseEntity<GalleryEntity> saveGallery(@RequestBody GalleryReqDto galleryReqDto) {
        GalleryEntity saveGallery = galleryService.saveGallery(galleryReqDto);
        return ResponseEntity.ok(saveGallery);
    }

    @GetMapping("/images")
    public ResponseEntity<List<GalleryResDto>> getImagesByEmail(@RequestParam String email) {
        List<GalleryResDto> galleries = galleryService.getImagesByEmail(email);
        return ResponseEntity.ok(galleries);
    }
}
