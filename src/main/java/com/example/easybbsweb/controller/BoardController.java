package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.Board;
import com.example.easybbsweb.service.BoardService;
import com.example.easybbsweb.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    @PostMapping("/loadBoard")
    public ResultInfo getBoard(@RequestBody Map map){
        String type= (String) map.get("type");
        Integer t=Integer.parseInt(type);
        switch (t){
            case 0:
                List<Board> firstLevelBoard = boardService.getFirstLevelBoard();
                return new ResultInfo(true,"响应成功",firstLevelBoard);
            case 1:
                break;

        }

        return new ResultInfo(false,"响应失败,请指定菜单级别",null);
    }
}
