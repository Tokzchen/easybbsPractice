package com.example.easybbsweb.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumUser {
    @Id
    private String email;
    private List<String> followers;
    private List<String> following;
    private List<String> articles;
}
