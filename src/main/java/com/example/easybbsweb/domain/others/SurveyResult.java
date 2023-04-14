package com.example.easybbsweb.domain.others;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SurveyResult {
    private String summary;
    private String area;
    private List<String> advice=new ArrayList<>();
}
