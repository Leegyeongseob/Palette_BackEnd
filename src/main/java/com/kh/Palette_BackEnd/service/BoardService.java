package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.BoardReqDto;
import com.kh.Palette_BackEnd.dto.resdto.BoardResDto;
import com.kh.Palette_BackEnd.entity.BoardEntity;
import com.kh.Palette_BackEnd.entity.MemberEntity;
import com.kh.Palette_BackEnd.entity.BoardListEntity;
import com.kh.Palette_BackEnd.repository.BoardRepository;
import com.kh.Palette_BackEnd.repository.MemberRepository;
import com.kh.Palette_BackEnd.repository.BoardListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardListRepository boardListRepository;

    public List<BoardResDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(this::convertToResDto)
                .collect(Collectors.toList());
    }

    public BoardResDto getBoardById(Long id) {
        BoardEntity board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));
        return convertToResDto(board);
    }

    public BoardResDto createBoard(BoardReqDto boardReqDto) {
        MemberEntity member = memberRepository.findByEmail(boardReqDto.getMemberEmail())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        BoardListEntity boardList = boardListRepository.findById(boardReqDto.getBoardListId())
                .orElseThrow(() -> new RuntimeException("Board list not found"));

        BoardEntity board = BoardEntity.builder()
                .title(boardReqDto.getTitle())
                .imgUrl(boardReqDto.getImgUrl())
                .contents(boardReqDto.getContents())
                .member(member)
                .boardList(boardList)
                .build();

        boardRepository.save(board);
        return convertToResDto(board);
    }

    public BoardResDto updateBoard(Long id, BoardReqDto boardReqDto) {
        BoardEntity board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));
        board.setTitle(boardReqDto.getTitle());
        board.setImgUrl(boardReqDto.getImgUrl());
        board.setContents(boardReqDto.getContents());

        boardRepository.save(board);
        return convertToResDto(board);
    }

    public void deleteBoard(Long id) {
        BoardEntity board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found"));
        boardRepository.delete(board);
    }

    private BoardResDto convertToResDto(BoardEntity board) {
        return BoardResDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .regDate(board.getRegDate())
                .imgUrl(board.getImgUrl())
                .contents(board.getContents())
                .memberEmail(board.getMember().getEmail())
                .boardListId(board.getBoardList().getId())
                .build();
    }
}
