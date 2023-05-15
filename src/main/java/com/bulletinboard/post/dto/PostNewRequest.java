package com.bulletinboard.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class PostNewRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    protected PostNewRequest() {
    }

    @Builder
    public PostNewRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
