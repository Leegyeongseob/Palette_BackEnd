package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.DateCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateCourseRepository extends JpaRepository<DateCourseEntity,Long> {
}
