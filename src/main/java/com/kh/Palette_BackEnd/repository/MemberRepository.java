package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Long, MemberEntity> {

}
