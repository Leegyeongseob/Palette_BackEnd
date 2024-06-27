package com.kh.Palette_BackEnd.repository;

import com.kh.Palette_BackEnd.entity.MemberEntity;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;


@SpringBootTest
@Transactional
@Slf4j
@TestPropertySource(locations = "classpath:application_test.properties")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;
    public MemberEntity createMemberInfo(){
        return MemberEntity.builder().email("can34879@naver.com")
                .pwd("1q2w3e4r!@")
                .name("이경섭")
                .nickName("멘도롱또돗")
                .coupleName("알콩달콩")
                .registrationNumber(9403101)
                .build();
    }
    @Test
    @DisplayName("회원가입테스트를 위한 데이터 입력")
    public void InsertMemberInfo(){
        MemberEntity user = createMemberInfo();
        memberRepository.saveAndFlush(user);
        em.clear();
        Optional<MemberEntity> member = memberRepository.findByEmail("can34879@naver.com");
        if (member.isPresent()){
            log.info("존재합니다."+ member.get());
        }
    }
}