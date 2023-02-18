package com.example.easybbsweb.domain.others;

import com.example.easybbsweb.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private Integer totalCnt;
    private Integer pageNo;
    private List<Article> list;
}
