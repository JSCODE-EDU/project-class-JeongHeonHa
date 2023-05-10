package com.bulletinboard.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostNewRequest {

    private String title;
    private String content;

    private PostNewRequest() {
    }

    public PostNewRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
