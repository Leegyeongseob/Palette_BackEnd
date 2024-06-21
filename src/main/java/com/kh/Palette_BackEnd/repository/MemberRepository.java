package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository< MemberEntity,Long> {

}
