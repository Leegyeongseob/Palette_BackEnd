package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.BoardReqDto;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.dto.resdto.BoardResDto;
import com.kh.Palette_BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<BoardResDto> createBoard(@RequestBody BoardReqDto boardReqDto) {
        BoardResDto createdBoard = boardService.createBoard(boardReqDto);
        return ResponseEntity.ok(createdBoard);
    }

    @GetMapping("/load")
    public ResponseEntity<Page<BoardResDto>> getAllBoards(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardResDto> boards = boardService.getAllBoards(pageable);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResDto> getBoardById(@PathVariable Long id) {
        BoardResDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BoardResDto> updateBoard(@PathVariable Long id, @RequestBody BoardReqDto boardReqDto) {
        BoardResDto updatedBoard = boardService.updateBoard(id, boardReqDto);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBoard(@RequestParam Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }
}
