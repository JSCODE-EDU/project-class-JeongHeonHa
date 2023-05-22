package com.bulletinboard.post.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostNewRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private PostNewRequest() {
    }

    @Builder
    private PostNewRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
