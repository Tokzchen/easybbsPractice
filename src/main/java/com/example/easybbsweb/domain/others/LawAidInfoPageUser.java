package com.example.easybbsweb.domain.others;

import com.example.easybbsweb.domain.entity.AidProcess;
import com.example.easybbsweb.domain.entity.LawAid;
import com.example.easybbsweb.domain.entity.University;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawAidInfoPageUser {
    Long userId;
    //目前的求助领域
    String currentArea;
    //关联的高校信息
    University university;

    Integer lawAidCnt;
    //维权进度信息
    List<AidProcess> progress;

}
