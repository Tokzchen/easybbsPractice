package com.example.easybbsweb.domain.entity.dto;

import com.example.easybbsweb.domain.entity.YoufaMail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoufaMailDTO {
    Boolean hasUnchecked;
    List<YoufaMail> youfaMails;
}
