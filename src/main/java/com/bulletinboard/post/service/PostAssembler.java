package com.bulletinboard.post.service;

import com.bulletinboard.post.domain.Post;
import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateResponse;

public class PostAssembler {

    public static Post toEntity(PostNewRequest postNewRequest) {
        return Post.PostBuilder.aPost()
                .title(postNewRequest.getTitle())
                .content(postNewRequest.getContent())
                .build();
    }

    public static PostResponse toDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitleValue())
                .content(post.getContentValue())
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .build();
    }

    public static PostUpdateResponse toUpdateDto(Post post) {
        return PostUpdateResponse.builder()
                .id(post.getId())
                .title(post.getTitleValue())
                .content(post.getContentValue())
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .build();
    }
}
