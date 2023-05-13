package com.bulletinboard.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class PostNewRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 15, message = "제목은 1글자 이상 15글자 이햐여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 1, max = 1000, message = "제목은 1글자 이상 1000글자 이하여야 합니다.")
    private String content;

    protected PostNewRequest() {
    }

    @Builder
    public PostNewRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
