package com.kh.Palette_BackEnd.controller;

import com.kh.Palette_BackEnd.dto.reqdto.BoardReqDto;
import com.kh.Palette_BackEnd.dto.resdto.BoardResDto;
import com.kh.Palette_BackEnd.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
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

    @PostMapping
    public ResponseEntity<BoardResDto> createBoard(@RequestBody BoardReqDto boardReqDto) {
        try {
            BoardResDto createdBoard = boardService.createBoard(boardReqDto);
            return ResponseEntity.ok(createdBoard);
        } catch (Exception e) {
            log.error("게시글 생성 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResDto> updateBoard(@PathVariable Long id, @RequestBody BoardReqDto boardReqDto) {
        try {
            BoardResDto updatedBoard = boardService.updateBoard(id, boardReqDto);
            return ResponseEntity.ok(updatedBoard);
        } catch (Exception e) {
            log.error("게시글 수정 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("게시글 삭제 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
