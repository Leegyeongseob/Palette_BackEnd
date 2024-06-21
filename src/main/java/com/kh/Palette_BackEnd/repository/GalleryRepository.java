package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.GalleryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GalleryRepository extends JpaRepository<GalleryEntity,Long> {
}
