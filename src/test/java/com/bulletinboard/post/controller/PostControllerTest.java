package com.bulletinboard.post.controller;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private PostService postService;

    @Test
    @DisplayName("게시글을 저장한다.")
    void savePost() throws Exception {
        //given
        Long id = 1L;
        PostNewRequest request = PostNewRequest.builder()
                .title("title")
                .content("content")
                .build();

        PostResponse response = createPostResponse(id, now());

        when(postService.savePost(any(PostNewRequest.class))).thenReturn(id);
        when(postService.findPostById(id)).thenReturn(response);

        //when //then
        mockMvc.perform(post("/posts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"));
    }

    @DisplayName("제목이 비어있으면 예외를 발생시킨다.")
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

    @DisplayName("제목이 비어있으면 예외를 발생시킨다.")
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

    @Test
    @DisplayName("모든 게시글을 찾는다.")
    void findPosts() throws Exception {
        //given
        Slice<PostResponse> slice = new SliceImpl<>(List.of(createPostResponse(1L, now()), createPostResponse(2L, now())));

        when(postService.findPosts(any(Pageable.class))).thenReturn(slice);

        //when //then
        mockMvc.perform(get("/posts")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L));
    }

    @DisplayName("101개 이상의 게시글을 조회하면 예외가 발생한다.")
    @Test
    void findPosts_Ex() throws Exception {
        int page = 0;
        int size = 101;
        String sort = "createdDate";
        Sort.Direction direction = Sort.Direction.DESC;

        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
                        .param("direction", direction.name())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("id로 게시글을 찾는다.")
    void findPostById() throws Exception {
        //given
        PostResponse post = new PostResponse(1L, "title", "content", now(), now());

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
        SliceImpl<PostResponse> slice = new SliceImpl<>(List.of(createPostResponse(1L, now()), createPostResponse(2L, now())));
        when(postService.findPostsByKeyword(anyString(), any(Pageable.class))).thenReturn(slice);

        //when //then
        mockMvc.perform(get("/posts/word")
                        .param("keyword", "t")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @DisplayName("키워드가 비어있으면 예외가 발생한다.")
    @Test
    void findPostByKeyword_NOTBLANK_EX() throws Exception {
        //given
        SliceImpl<PostResponse> slice = new SliceImpl<>(List.of(createPostResponse(1L, now()), createPostResponse(2L, now())));
        when(postService.findPostsByKeyword(anyString(), any(Pageable.class))).thenReturn(slice);

        //when //then
        mockMvc.perform(get("/posts/word")
                        .param("keyword", "")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("게시글을 변경한다.")
    void updatePost() throws Exception {
        //given
        PostUpdateRequest request = new PostUpdateRequest("title1", "content1");
        PostResponse response = new PostResponse(1L, "title1", "content1", now(), now());

        given(postService.findPostById(1L)).willReturn(response);

        //when //then
        mockMvc.perform(put("/posts/{id}", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.content").value("content1"));
    }

    @Test
    @DisplayName("게시글을 삭제한다.")
    void deletePost() throws Exception {
        //given
        Long id = 1L;

        //when //then
        mockMvc.perform(delete("/posts/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

    }

    private static PostResponse createPostResponse(long id, LocalDateTime now) {
        return PostResponse.builder()
                .id(id)
                .title("title")
                .content("content")
                .createdDate(now)
                .build();
    }
}