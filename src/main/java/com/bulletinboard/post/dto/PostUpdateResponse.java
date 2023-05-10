package com.bulletinboard.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostUpdateResponse {

    private Long id;
    private String title;
    private String content;

    private PostUpdateResponse() {
    }

    public PostUpdateResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
