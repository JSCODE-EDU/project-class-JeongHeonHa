package com.bulletinboard.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostUpdateRequest {

    private String title;
    private String content;

    private PostUpdateRequest() {
    }

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
