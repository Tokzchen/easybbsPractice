package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.Board;
import com.example.easybbsweb.mapper.ForumBoardMapper;
import com.example.easybbsweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    ForumBoardMapper mapper;
    @Override
    public List<Board> getFirstLevelBoard() {
        List<Board> boards = mapper.selectByPBoardId("0");
        return boards;
    }

    @Override
    public List<Board> getSecondLevelBoardByBoardId(String boardId) {
        return null;
    }
}
