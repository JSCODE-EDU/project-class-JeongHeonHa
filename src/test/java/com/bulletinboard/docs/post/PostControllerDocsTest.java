package com.bulletinboard.docs.post;

import com.bulletinboard.docs.RestDocsSupport;
import com.bulletinboard.post.controller.PostController;
import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerDocsTest extends RestDocsSupport {

    private final PostService postService = mock(PostService.class);
    
    @Override
    protected Object initController() {
        return new PostController(postService);
    }
    
    @DisplayName("게시글을 등록하기 위한 API")
    @Test
    void createPost() throws Exception {
        //given
        Long id = 1L;
        PostNewRequest request = PostNewRequest.builder()
                .title("title")
                .content("content")
                .build();

        PostResponse response = createPostResponse(id, now());

        given(postService.savePost(any(PostNewRequest.class))).willReturn(id);
        given(postService.findPostById(id)).willReturn(response);

        //when //then
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.content").value("content"))
                .andDo(document("post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                        .description("게시글 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("createdDate").type(JsonFieldType.ARRAY)
                                        .description("게시글 생성일"),
                                fieldWithPath("updatedDate").type(JsonFieldType.ARRAY)
                                        .description("게시글 수정일")
                        )
                ));

    }

    private static PostResponse createPostResponse(long id, LocalDateTime now) {
        return PostResponse.builder()
                .id(id)
                .title("title")
                .content("content")
                .createdDate(now)
                .updatedDate(now)
                .build();
    }
}
