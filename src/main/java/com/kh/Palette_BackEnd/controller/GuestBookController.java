package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.GuestBookReqDto;
import com.kh.Palette_BackEnd.dto.resdto.GuestBookResDto;
import com.kh.Palette_BackEnd.entity.GuestBookEntity;
import com.kh.Palette_BackEnd.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guestbook")
public class GuestBookController {

    @Autowired
    private GuestBookService guestBookService;

    @GetMapping("/{coupleName}")
    public ResponseEntity<List<GuestBookResDto>> getGuestBookEntries(@PathVariable String coupleName) {
        List<GuestBookEntity> entries = guestBookService.getGuestBookEntries(coupleName);
        List<GuestBookResDto> responseDtoList = entries.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<GuestBookResDto> addGuestBookEntry(@RequestBody GuestBookReqDto guestBookReqDto) {
        GuestBookEntity newEntry = guestBookService.addGuestBookEntry(
                guestBookReqDto.getCoupleName(),
                guestBookReqDto.getMemberEmail(),
                guestBookReqDto.getContents()
        );
        GuestBookResDto responseDto = convertToResponseDto(newEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{entryId}")
    public ResponseEntity<Void> updateGuestBookEntry(
            @PathVariable Long entryId,
            @RequestBody GuestBookReqDto guestBookReqDto
    ) {
        guestBookService.updateGuestBookEntry(
                entryId,
                guestBookReqDto.getContents(),
                guestBookReqDto.getMemberEmail()
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> deleteGuestBookEntry(
            @PathVariable Long entryId,
            @RequestParam String memberEmail
    ) {
        guestBookService.deleteGuestBookEntry(entryId, memberEmail);
        return ResponseEntity.noContent().build();
    }

    private GuestBookResDto convertToResponseDto(GuestBookEntity entity) {
        return GuestBookResDto.builder()
                .id(entity.getId())
                .regDateTime(entity.getRegDate())
                .contents(entity.getContents())
                .memberNickName(entity.getMember().getNickName())
                .imgUrl(entity.getMember().getProfileImgUrl())
                .coupleName(entity.getCouple().getCoupleName())
                .build();
    }
}
