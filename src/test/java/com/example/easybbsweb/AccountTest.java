package com.example.easybbsweb;

import com.example.easybbsweb.domain.IdSelector;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.utils.GenerateIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AccountTest {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserMainMapper userMainMapper;
}
