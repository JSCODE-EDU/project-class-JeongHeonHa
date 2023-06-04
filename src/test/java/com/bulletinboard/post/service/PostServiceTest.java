package com.bulletinboard.post.service;

import com.bulletinboard.IntegrationTestSupport;
import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.exception.PostNotFoundException;
import com.bulletinboard.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import static org.assertj.core.api.Assertions.*;

class PostServiceTest extends IntegrationTestSupport {


    @Autowired PostService postService;

    @Autowired PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
    }

    private PostNewRequest createPostNewRequest() {
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

    @DisplayName("게시글을 저장한다.")
    @Test
    void savePostTest() {
        //given
        PostNewRequest request = createPostNewRequest();
        Long savedId = postService.savePost(request);

        //when
        PostResponse postResponse = postService.findPostById(savedId);

        //then
        assertThat(savedId).isEqualTo(postResponse.getId());
    }

    @DisplayName("0번째부터 100개의 게시글을 시간 순으로 반환한다.")
    @Test
    void findPostsTest() {
        //given
        for (int i=0; i < 100; i++) {
            postService.savePost(createPostNewRequest());
        }

        Pageable pageable = PageRequest.of(0, 100);

        //when
        Slice<PostResponse> result = postService.findPosts(pageable);

        //then
        assertThat(result.getSize()).isEqualTo(100);
    }

    @DisplayName("게시글 하나를 조회한다.")
    @Test
    void findByIdTest() {
        //given
        PostNewRequest post = createPostNewRequest();
        Long savedId = postService.savePost(post);

        //when
        PostResponse result = postService.findPostById(savedId);

        //then
        assertThat(result.getId()).isEqualTo(savedId);
    }

    @DisplayName("게시글이 없으면 PostNotFoundException이 발생한다.")
    @Test
    void findById_Ex() {
        //given
        Long id = 1L;

        //when //then
        assertThatThrownBy(() -> postService.findPostById(id))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("게시글을 변경한다.")
    @Test
    void updatePostTest() {
        //given
        Long savedId = postService.savePost(createPostNewRequest());
        PostUpdateRequest updatedPost = createUpdatedPost();

        //when
        postService.updatePost(savedId, updatedPost);
        PostResponse postResponse = postService.findPostById(savedId);

        //then
        assertThat(postResponse.getTitle()).isEqualTo("updatedTitle");
        assertThat(postResponse.getContent()).isEqualTo("updatedContent");
    }

    @DisplayName("변경할 게시글이 없으면 PostNotFoundException이 발생한다.")
    @Test
    void updatePost_Ex() {
        //given
        Long id = 1L;
        PostUpdateRequest updatedPost = createUpdatedPost();

        //when //then
        assertThatThrownBy(() -> postService.updatePost(id, updatedPost))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("게시글을 제거한다.")
    @Test
    void deletePostByIdTest() {
        //given
        PostNewRequest post = createPostNewRequest();
        Long savedId = postService.savePost(post);

        //when
        postService.deletePostById(savedId);

        //then
        assertThatThrownBy(() -> postService.findPostById(savedId))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("제거할 게시글이 없으면 PostNotFoundException이 발생한다.")
    @Test
    void deletePostById_Ex() {
        //given
        Long id = 1L;

        //when //then
        assertThatThrownBy(() -> postService.deletePostById(id))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("제목에 키워드를 포함한 모든 게시글을 반환한다.")
    @Test
    void findPostsByKeyword() {
        //given
        for (int i=0; i < 3; i++) {
            PostNewRequest request = createPostNewRequest();
            postService.savePost(request);
        }

        //when
        Slice<PostResponse> result = postService.findPostsByKeyword("t", null);

        //then
        assertThat(result.getSize()).isEqualTo(3);
    }
}