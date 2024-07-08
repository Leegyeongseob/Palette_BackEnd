package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.GalleryEntity;
import com.kh.Palette_BackEnd.entity.GalleryListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GalleryListRepository extends JpaRepository<GalleryListEntity,Long> {
    Optional<GalleryListEntity> findByGallery(GalleryEntity gallery);
}
