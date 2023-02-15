package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.Board;

import java.util.List;

public interface BoardService {
    List<Board> getFirstLevelBoard();

    List<Board> getSecondLevelBoardByBoardId(String boardId);
}
