package com.example.easybbsweb.router.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LikeMessage {
    // article_id
    private Long id;
    private Long uid;

    private boolean like;
    private String identifies;
}
