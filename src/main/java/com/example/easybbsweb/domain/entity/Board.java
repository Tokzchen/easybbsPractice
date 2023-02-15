package com.example.easybbsweb.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private String boardId;//模块id
    private String pBoardId;//父模块id
    private String boardName;
    private String cover;
    private String boardDesc;//模块描述
    private String sort;
    private String postType;//模块获取post的方法传的type值
}
