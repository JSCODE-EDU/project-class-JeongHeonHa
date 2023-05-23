package com.bulletinboard.post.controller;

import com.bulletinboard.ControllerTestSupport;
import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest extends ControllerTestSupport {

    @DisplayName("게시글을 저장한다.")
    @Test
    void savePost() throws Exception {
        //given
        Long id = 1L;
        PostNewRequest request = PostNewRequest.builder()
                .title("title")
                .content("content")
                .build();

        PostResponse response = createPostResponse(id, createTime());

        given(postService.savePost(any(PostNewRequest.class))).willReturn(id);
        given(postService.findPostById(id)).willReturn(response);

        //when //then
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.title").value("title"),
                        jsonPath("$.content").value("content")
                );
    }

    @DisplayName("제목이 비어있으면 400예외를 발생시킨다.")
    @Test
    void createPost_Title_Blank_Ex() throws Exception {
        //given
        PostNewRequest request = PostNewRequest.builder()
                .title("")
                .content("content")
                .build();

        //when //then
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @DisplayName("내용이 비어있으면 400예외를 발생시킨다.")
    @Test
    void createPost_Content_Blank_Ex() throws Exception {
        //given
        PostNewRequest request = PostNewRequest.builder()
                .title("title")
                .content("")
                .build();

        //when //then
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @DisplayName("모든 게시글을 찾는다.")
    @Test
    void findPosts() throws Exception {
        //given
        Slice<PostResponse> slice = new SliceImpl<>(List.of(
                createPostResponse(1L, createTime()),
                createPostResponse(2L, createTime()))
        );

        given(postService.findPosts(any(Pageable.class))).willReturn(slice);

        //when //then
        mockMvc.perform(get("/posts")
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content[0].id").value(1L),
                        jsonPath("$.content[1].id").value(2L)
                );
    }

    @DisplayName("101개 이상의 게시글을 조회하면 400예외가 발생한다.")
    @Test
    void findPosts_Ex() throws Exception {
        //given
        int page = 0;
        int size = 101;
        String sort = "createdDate";
        Sort.Direction direction = Sort.Direction.DESC;

        //when //then
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
                        .param("direction", direction.name())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @DisplayName("id로 하나의 게시글을 찾는다.")
    @Test
    void findPostById() throws Exception {
        //given
        PostResponse post = PostResponse.builder()
                .id(1L)
                .title("title")
                .content("content")
                .createdDate(createTime())
                .updatedDate(createTime())
                .build();

        given(postService.findPostById(1L)).willReturn(post);

        //when //then
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @DisplayName("키워드로 게시글을 찾는다.")
    @Test
    void findPostByKeyword() throws Exception {
        //given
        SliceImpl<PostResponse> slice = new SliceImpl<>(List.of(
                createPostResponse(1L, createTime()),
                createPostResponse(2L, createTime()))
        );

        given(postService.findPostsByKeyword(anyString(), any(Pageable.class))).willReturn(slice);

        //when //then
        mockMvc.perform(get("/posts/word")
                        .param("keyword", "t")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @DisplayName("키워드가 비어있으면 400예외가 발생한다.")
    @Test
    void findPostByKeyword_NOTBLANK_EX() throws Exception {
        //given
        SliceImpl<PostResponse> slice = new SliceImpl<>(List.of(
                createPostResponse(1L, createTime()),
                createPostResponse(2L, createTime()))
        );

        given(postService.findPostsByKeyword(anyString(), any(Pageable.class))).willReturn(slice);

        //when //then
        mockMvc.perform(get("/posts/word")
                        .param("keyword", "")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @DisplayName("게시글을 변경한다.")
    @Test
    void updatePost() throws Exception {
        //given
        PostUpdateRequest request = PostUpdateRequest.builder()
                .title("updatedTitle")
                .content("updatedContent")
                .build();

        PostResponse response = PostResponse.builder()
                .id(1L)
                .title("updatedTitle")
                .content("updatedContent")
                .createdDate(createTime())
                .updatedDate(createTime())
                .build();

        given(postService.findPostById(1L)).willReturn(response);

        //when //then
        mockMvc.perform(put("/posts/{id}", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.title").value("updatedTitle"),
                        jsonPath("$.content").value("updatedContent")
                );
    }

    @DisplayName("게시글을 삭제한다.")
    @Test
    void deletePost() throws Exception {
        //given
        Long id = 1L;

        //when //then
        mockMvc.perform(delete("/posts/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

    }

    private PostResponse createPostResponse(long id, LocalDateTime now) {
        return PostResponse.builder()
                .id(id)
                .title("title")
                .content("content")
                .createdDate(now)
                .updatedDate(now)
                .build();
    }

    private LocalDateTime createTime() {
        return LocalDateTime.of(2022, 02, 22, 22, 22, 22);
    }
}