package com.example.easybbsweb.domain.others.lawAid;

import com.example.easybbsweb.domain.entity.LawAid;
import com.example.easybbsweb.domain.entity.dto.UserDTO1;
import com.example.easybbsweb.domain.entity.dto.UserDTO2;
import com.example.easybbsweb.domain.others.lawAid.ApplyUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawAidInfoPageUni {
    List<LawAid>  totalLawAids;
    List<UserDTO2> usersToConfirm;
    List<UserDTO2> userConfirmed;
    List<UserDTO1>  recommendUsers;

}
