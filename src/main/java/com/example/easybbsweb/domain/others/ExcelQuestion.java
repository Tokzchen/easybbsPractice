package com.example.easybbsweb.domain.others;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelQuestion {
    private Integer parentId;
    private String path;
    private String content;
    private String nodeType;
    private String advice;
    private Integer rate;
    private Integer importance;
}
