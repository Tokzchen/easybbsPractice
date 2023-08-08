package com.example.easybbsweb.router;

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
    // true like +1
    // false unlike -1
    private Integer likeCount;
}
