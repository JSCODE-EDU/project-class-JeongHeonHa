package com.bulletinboard.post.service;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.dto.PostUpdateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostService postService;

    private PostNewRequest createPost() {
        return new PostNewRequest("title", "content");
    }

    private PostUpdateRequest createUpdatedPost() {
        return new PostUpdateRequest("updatedTitle", "updatedContent");
    }

    @Test
    @DisplayName("savePost 메서드는 Post를 저장한다.")
    void savePostTest() {
        //given
        PostNewRequest post = createPost();
        Long savedId = postService.savePost(post);

        //when
        PostResponse postResponse = postService.findById(savedId);

        //then
        assertThat(savedId).isEqualTo(postResponse.getId());
    }

    @Test
    @DisplayName("findAllPosts 메서드는 모든 Post를 반환한다.")
    void findAllPostsTest() {
        //given
        PostNewRequest post1 = createPost();
        PostNewRequest post2 = createPost();
        PostNewRequest post3 = createPost();
        PostNewRequest post4 = createPost();

        postService.savePost(post1);
        postService.savePost(post2);
        postService.savePost(post3);
        postService.savePost(post4);

        //when
        List<PostResponse> result = postService.findAllPosts();

        //then
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("findById 메서드는 id를 제공하면 id에 맞는 Post를 반환한다.")
    void findByIdTest() {
        //given
        PostNewRequest post = createPost();
        Long savedId = postService.savePost(post);

        //when
        PostResponse result = postService.findById(savedId);

        //then
        assertThat(result.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("findUpdatedPostById 메서드는 id를 제공하면 id에 맞는 PostUpdateResponse를 반환한다.")
    void findUpdatedPostByIdTest() {
        //given
        Long savedId = postService.savePost(createPost());
        PostUpdateRequest updatedPost = createUpdatedPost();

        //when
        postService.updatePost(savedId, updatedPost);
        PostUpdateResponse postUpdateResponse = postService.findUpdatedPostById(savedId);

        //then
        assertThat(postUpdateResponse.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("updatePost 메서드는 id와 변경된 Post를 제공하면 Post를 변경한다.")
    void updatePostTest() {
        //given
        Long savedId = postService.savePost(createPost());
        PostUpdateRequest updatedPost = createUpdatedPost();

        //when
        postService.updatePost(savedId, updatedPost);
        PostUpdateResponse postUpdateResponse = postService.findUpdatedPostById(savedId);

        //then
        assertThat(postUpdateResponse.getTitle()).isEqualTo("updatedTitle");
        assertThat(postUpdateResponse.getContent()).isEqualTo("updatedContent");
    }

    @Test
    @DisplayName("deletePost 메서드는 id를 제공하면 id에 맞는 Post를 제거한다.")
    void deletePostByIdTest() {
        //given
        PostNewRequest post = createPost();
        Long savedId = postService.savePost(post);

        //when
        postService.deletePostById(savedId);

        //then
        assertThrows(IllegalStateException.class,
                () -> postService.findById(savedId));
    }
}