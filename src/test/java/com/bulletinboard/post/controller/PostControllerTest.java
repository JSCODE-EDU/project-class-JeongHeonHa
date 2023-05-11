package com.bulletinboard.post.controller;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.dto.PostUpdateResponse;
import com.bulletinboard.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private PostService postService;

    @Test
    @DisplayName("게시글을 저장한다.")
    void savePost() throws Exception {
        //given
        PostNewRequest request = new PostNewRequest("title", "content");

        //when //then
        mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("모든 게시글을 찾는다.")
    void findAllPosts() throws Exception {
        //given
        List<PostResponse> posts = new ArrayList<>();
        posts.add(new PostResponse(1L, "title1", "content1"));

        given(postService.findAllPosts()).willReturn(posts);

        //when //then
        mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("id로 게시글을 찾는다.")
    void findPostById() throws Exception {
        //given
        PostResponse post = new PostResponse(1L, "title", "content");

        given(postService.findById(1L)).willReturn(post);

        //when //then
        mockMvc.perform(get("/posts/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글을 변경한다.")
    void updatePost() throws Exception {
        //given
        PostUpdateRequest request = new PostUpdateRequest("title1", "content1");
        PostUpdateResponse response = new PostUpdateResponse(1L, "title1", "content1");

        given(postService.findUpdatedPostById(1L)).willReturn(response);

        //when //then
        mockMvc.perform(post("/posts/edit/{id}", 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
}