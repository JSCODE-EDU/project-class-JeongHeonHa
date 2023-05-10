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
        return new PostResponse(post.getId(), post.getTitleValue(), post.getContentValue());
    }

    public static PostUpdateResponse toUpdateDto(Post post) {
        return new PostUpdateResponse(post.getId(), post.getTitleValue(), post.getContentValue());
    }
}
