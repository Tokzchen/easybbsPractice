package com.example.easybbsweb.domain.others.lawAid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoPair {
    Long useId;
    AtomicInteger cnt;
}
