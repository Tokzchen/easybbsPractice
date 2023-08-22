package com.example.easybbsweb.domain.others.lawAid;

import com.example.easybbsweb.domain.entity.AidProcess;
import com.example.easybbsweb.domain.entity.LawAid;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.entity.UserMain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyUserInfo {
    UserInfo userInfo;
    UserMain userMain;
    LawAid lawAid;
    List<AidProcess> aidProcesses;
}
