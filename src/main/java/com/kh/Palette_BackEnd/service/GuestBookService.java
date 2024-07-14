package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.entity.CoupleEntity;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.repository.CoupleRepository;
import com.kh.Palette_BackEnd.repository.GuestBookRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestBookService {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Autowired
    private CoupleRepository coupleRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 특정 커플의 방명록 항목들을 가져오는 메서드
    public List<GuestBookEntity> getGuestBookEntries(String coupleName) {
        CoupleEntity couple = coupleRepository.findByCoupleName(coupleName)
                .orElseThrow(() -> new RuntimeException("Couple not found"));
        return guestBookRepository.findByCouple(couple);
    }

    // 방명록 항목을 추가하는 메서드
    public GuestBookEntity addGuestBookEntry(String coupleName, String memberEmail,String contents) {
        CoupleEntity couple = coupleRepository.findByCoupleName(coupleName)
                .orElseThrow(() -> new RuntimeException("Couple not found"));
        MemberEntity member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        GuestBookEntity entry = GuestBookEntity.builder()
                .couple(couple)
                .member(member)
                .contents(contents)
                .build();

        return guestBookRepository.save(entry);
    }

    // 방명록 항목을 수정하는 메서드
    public void updateGuestBookEntry(Long entryId,String contents , String requesterEmail) {
        GuestBookEntity entry = guestBookRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        CoupleEntity couple = entry.getCouple();
        if (!entry.getMember().getEmail().equals(requesterEmail) &&
                !couple.getFirstEmail().equals(requesterEmail) &&
                !couple.getSecondEmail().equals(requesterEmail)) {
            throw new RuntimeException("Only the author or couple members can update this entry");
        }


        entry.setContents(contents);
        guestBookRepository.save(entry);
    }

    // 방명록 항목을 삭제하는 메서드
    public void deleteGuestBookEntry(Long entryId, String requesterEmail) {
        GuestBookEntity entry = guestBookRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        CoupleEntity couple = entry.getCouple();
        if (!entry.getMember().getEmail().equals(requesterEmail) &&
                !couple.getFirstEmail().equals(requesterEmail) &&
                !couple.getSecondEmail().equals(requesterEmail)) {
            throw new RuntimeException("Only the author or couple members can delete this entry");
        }

        guestBookRepository.delete(entry);
    }
}
