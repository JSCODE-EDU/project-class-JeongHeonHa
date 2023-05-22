package com.bulletinboard.post.service;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.exception.PostNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostService postService;

    private PostNewRequest createPost() {
        return PostNewRequest.builder()
                .title("title")
                .content("content")
                .build();
    }

    private PostUpdateRequest createUpdatedPost() {
        return PostUpdateRequest.builder()
                .title("updatedTitle")
                .content("updatedContent")
                .build();
    }

    @Test
    @DisplayName("post를 저장한다.")
    void savePostTest() {
        //given
        PostNewRequest post = createPost();
        Long savedId = postService.savePost(post);

        //when
        PostResponse postResponse = postService.findPostById(savedId);

        //then
        assertThat(savedId).isEqualTo(postResponse.getId());
    }

    @Test
    @DisplayName("0번째부터 100개의 post를 시간 순으로 반환한다.")
    void findPostsTest() {
        //given
        for (int i=0; i < 100; i++) {
            postService.savePost(createPost());
        }

        Pageable pageable = PageRequest.of(0, 100);

        //when
        Slice<PostResponse> result = postService.findPosts(pageable);

        //then
        assertThat(result.getSize()).isEqualTo(100);
    }

    @Test
    @DisplayName("post를 조회한다.")
    void findByIdTest() {
        //given
        PostNewRequest post = createPost();
        Long savedId = postService.savePost(post);

        //when
        PostResponse result = postService.findPostById(savedId);

        //then
        assertThat(result.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("post가 없으면 PostNotFoundException 발생")
    void findById_Ex() {
        //given
        Long id = 1L;

        //when //then
        assertThatThrownBy(() -> postService.findPostById(id))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    @DisplayName("post를 변경한다.")
    void updatePostTest() {
        //given
        Long savedId = postService.savePost(createPost());
        PostUpdateRequest updatedPost = createUpdatedPost();

        //when
        postService.updatePost(savedId, updatedPost);
        PostResponse postResponse = postService.findPostById(savedId);

        //then
        assertThat(postResponse.getTitle()).isEqualTo("updatedTitle");
        assertThat(postResponse.getContent()).isEqualTo("updatedContent");
    }

    @Test
    @DisplayName("변경할 post가 없으면 PostNotFoundException 발생")
    void updatePost_Ex() {
        //given
        Long id = 1L;
        PostUpdateRequest updatedPost = createUpdatedPost();
        //when //then
        assertThatThrownBy(() -> postService.updatePost(id, updatedPost))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    @DisplayName("post를 제거한다.")
    void deletePostByIdTest() {
        //given
        PostNewRequest post = createPost();
        Long savedId = postService.savePost(post);

        //when
        postService.deletePostById(savedId);

        //then
        assertThatThrownBy(() -> postService.findPostById(savedId))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    @DisplayName("제거할 post가 없으면 PostNotFoundException 발생")
    void deletePostById_Ex() {
        //given
        Long id = 1L;

        //when //then
        assertThatThrownBy(() -> postService.deletePostById(id))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    @DisplayName("제목에 keyword를 포함한 모든 post를 반환한다.")
    void findPostsByKeyword() {
        //given
        for (int i=0; i < 3; i++) {
            PostNewRequest request = createPost();
            postService.savePost(request);
        }

        //when
        Slice<PostResponse> result = postService.findPostsByKeyword("t", null);

        //then
        assertThat(result.getSize()).isEqualTo(3);
    }
}