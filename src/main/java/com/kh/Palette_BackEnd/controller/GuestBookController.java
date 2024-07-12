package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.GuestBookReqDto;
import com.kh.Palette_BackEnd.dto.resdto.GuestBookResDto;
import com.kh.Palette_BackEnd.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guestbook")
public class GuestBookController {

    @Autowired
    private GuestBookService guestBookService;

    @GetMapping
    public ResponseEntity<List<GuestBookResDto>> getAllGuestBooks() {
        List<GuestBookResDto> guestBooks = guestBookService.getAllGuestBooks();
        return ResponseEntity.ok(guestBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestBookResDto> getGuestBookById(@PathVariable Long id) {
        Optional<GuestBookResDto> guestBook = guestBookService.getGuestBookById(id);
        return guestBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{coupleName}")
    public ResponseEntity<List<GuestBookResDto>> getGuestBooksByCoupleName(@PathVariable String coupleName) {
        List<GuestBookResDto> guestBooks = guestBookService.getGuestBooksByCoupleName(coupleName);
        return ResponseEntity.ok(guestBooks);
    }

    @PostMapping
    public ResponseEntity<GuestBookResDto> createGuestBook(@RequestBody GuestBookReqDto guestBookReqDto) {
        GuestBookResDto createdGuestBook = guestBookService.saveOrUpdateGuestBook(guestBookReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGuestBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestBookResDto> updateGuestBook(@PathVariable Long id, @RequestBody GuestBookReqDto guestBookReqDto) {
        GuestBookResDto updatedGuestBook = guestBookService.saveOrUpdateGuestBook(guestBookReqDto);
        return ResponseEntity.ok(updatedGuestBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuestBook(@PathVariable Long id) {
        guestBookService.deleteGuestBook(id);
        return ResponseEntity.noContent().build();
    }
}
