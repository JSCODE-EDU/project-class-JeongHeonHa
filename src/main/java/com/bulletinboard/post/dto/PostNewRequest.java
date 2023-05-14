package com.bulletinboard.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostNewRequest {

    private String title;
    private String content;

    protected PostNewRequest() {
    }

    @Builder
    public PostNewRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
