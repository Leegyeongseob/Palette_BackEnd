package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.BoardReqDto;
import com.kh.Palette_BackEnd.dto.resdto.BoardResDto;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //게시글 생성 기능
    @Transactional
    public BoardResDto createBoard(BoardReqDto boardReqDto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(boardReqDto.getTitle());
        boardEntity.setContents(boardReqDto.getContents());
        boardEntity.setImgUrl(boardReqDto.getImgUrl());

        BoardEntity savedEntity = boardRepository.save(boardEntity);
        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setId(savedEntity.getId());
        boardResDto.setTitle(savedEntity.getTitle());
        boardResDto.setRegDate(savedEntity.getRegDate());
        return boardResDto;
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<BoardResDto> getAllBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardEntity> boardEntities = boardRepository.findAll(pageable);
        return boardEntities.map(boardEntity -> {
            BoardResDto boardResDto = new BoardResDto();
            boardResDto.setId(boardEntity.getId());
            boardResDto.setTitle(boardEntity.getTitle());
            boardResDto.setRegDate(boardEntity.getRegDate());
            return boardResDto;
        });
    }


    // 게시글 상세보기
    @Transactional(readOnly = true)
    public BoardResDto getBoardDetail(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id " + id));

        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setId(boardEntity.getId());
        boardResDto.setTitle(boardEntity.getTitle());
        boardResDto.setContents(boardEntity.getContents());
        boardResDto.setRegDate(boardEntity.getRegDate());
        boardResDto.setImgUrl(boardEntity.getImgUrl());

        return boardResDto;
    }

    // 게시글 수정 기능
    @Transactional
    public BoardResDto updateBoard(Long id, BoardReqDto boardReqDto) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id " + id));

        boardEntity.setTitle(boardReqDto.getTitle());
        boardEntity.setContents(boardReqDto.getContents());
        boardEntity.setImgUrl(boardReqDto.getImgUrl());

        BoardEntity updatedEntity = boardRepository.save(boardEntity);

        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setId(updatedEntity.getId());
        boardResDto.setTitle(updatedEntity.getTitle());
        boardResDto.setContents(updatedEntity.getContents());
        boardResDto.setRegDate(updatedEntity.getRegDate());
        boardResDto.setImgUrl(updatedEntity.getImgUrl());

        return boardResDto;
    }

    // 게시글 삭제 기능
    @Transactional
    public void deleteBoard(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id " + id));
        boardRepository.delete(boardEntity);
    }
}
