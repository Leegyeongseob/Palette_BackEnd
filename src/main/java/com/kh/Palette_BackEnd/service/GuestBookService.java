package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.GuestBookReqDto;
import com.kh.Palette_BackEnd.dto.resdto.GuestBookResDto;
import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.GuestBookRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GuestBookService {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Autowired
    private CoupleRepository coupleRepository;
    @Autowired
    private MemberRepository memberRepository;

    /**
     * 방명록을 생성하거나 수정하는 메소드
     */
    @Transactional
    public GuestBookResDto saveOrUpdateGuestBook(GuestBookReqDto guestBookReqDto) {
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(guestBookReqDto.getCoupleName());
        CoupleEntity couple = coupleEntityOpt.get();
        Optional<MemberEntity>  memberEntityOpt = memberRepository.findByEmail(guestBookReqDto.getMemberEmail());
        MemberEntity member = memberEntityOpt.get();
        if (guestBookReqDto.getId() == null) {
            // 방명록 생성
            GuestBookEntity guestBookEntity = new GuestBookEntity();
            guestBookEntity.setTitle(guestBookReqDto.getTitle());
            guestBookEntity.setContents(guestBookReqDto.getContents());
            guestBookEntity.setMember(member);
            guestBookEntity.setCouple(couple);

            GuestBookEntity savedEntity = guestBookRepository.save(guestBookEntity);
            return convertToDto(savedEntity);
        } else {
            // 방명록 수정
            Optional<GuestBookEntity> guestBookOpt = guestBookRepository.findById(guestBookReqDto.getId());
            GuestBookEntity guestBookEntity = guestBookOpt.orElseThrow(() -> new RuntimeException("GuestBook not found with id " + guestBookReqDto.getId()));

            guestBookEntity.setTitle(guestBookReqDto.getTitle());
            guestBookEntity.setContents(guestBookReqDto.getContents());

            GuestBookEntity updatedEntity = guestBookRepository.save(guestBookEntity);
            return convertToDto(updatedEntity);
        }
    }

    /**
     * 모든 방명록을 조회하는 메소드
     */
    public List<GuestBookResDto> getAllGuestBooks() {
        return guestBookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 ID로 방명록을 조회하는 메소드
     */
    public Optional<GuestBookResDto> getGuestBookById(Long id) {
        return guestBookRepository.findById(id)
                .map(this::convertToDto);
    }

    /**
     * 방명록을 삭제하는 메소드
     */
    @Transactional
    public void deleteGuestBook(Long id) {
        guestBookRepository.deleteById(id);
    }

    /**
     * Couple 이름으로 방명록 목록을 조회하는 메소드
     */
    public List<GuestBookResDto> getGuestBooksByCoupleName(String coupleName) {
        Optional<CoupleEntity> coupleEntityOpt = coupleRepository.findByCoupleName(coupleName);
        CoupleEntity couple = coupleEntityOpt.orElseThrow(() -> new RuntimeException("Couple not found with name: " + coupleName));

        List<GuestBookEntity> guestBooks = guestBookRepository.findByCoupleCoupleName(coupleName);
        return guestBooks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * GuestBookEntity를 GuestBookResDto로 변환하는 메소드
     */
    private GuestBookResDto convertToDto(GuestBookEntity guestBookEntity) {
        GuestBookResDto dto = new GuestBookResDto();
        dto.setId(guestBookEntity.getId());
        dto.setTitle(guestBookEntity.getTitle());
        dto.setContents(guestBookEntity.getContents());
        dto.setCoupleName(guestBookEntity.getCouple().getCoupleName());
        return dto;
    }
}
