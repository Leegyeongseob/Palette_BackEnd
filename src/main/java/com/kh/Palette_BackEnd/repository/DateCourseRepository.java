package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateCourseRepository extends JpaRepository<LocationEntity,Long> {
}
