package com.bulletinboard.post.controller;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.exception.InvalidPostException;
import com.bulletinboard.post.service.PostService;
import com.bulletinboard.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bulletinboard.utils.ApiUtils.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping
    public ResponseEntity<ApiResult<PostResponse>> savePost(@Valid @RequestBody PostNewRequest postNewRequest) {
        Long savedPostId = postService.savePost(postNewRequest);
        PostResponse postResponse = postService.findPostById(savedPostId);

        return ApiUtils.success(postResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "게시글 모두 조회", description = "게시글을 모두 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping
    public ResponseEntity<ApiResult<Slice<PostResponse>>> findPosts(
            @PageableDefault(size = 100, sort = "createdDate", direction = DESC) Pageable pageable) {

        if (pageable.getPageSize() > 100) {
            throw new InvalidPostException();
        }

        Slice<PostResponse> postResponses = postService.findPosts(pageable);
        return success(postResponses, HttpStatus.OK);
    }

    @Operation(summary = "게시글 키워드로 조회", description = "게시글을 키워드로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/word")
    public ResponseEntity<ApiResult<Slice<PostResponse>>> findPostsByKeyword(
            @RequestParam String keyword,
            @PageableDefault(size = 100, sort = "createdDate", direction = DESC) Pageable pageable) {

        if (pageable.getPageSize() > 100) {
            throw new InvalidPostException();
        }

        if (!StringUtils.hasText(keyword)) throw new InvalidPostException("키워드는 필수 입니다.");

        Slice<PostResponse> postResponses = postService.findPostsByKeyword(keyword, pageable);
        return success(postResponses, HttpStatus.OK);
    }

    @Operation(summary = "게시글 조회", description = "하나의 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<PostResponse>> findPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.findPostById(id);

        return success(postResponse, HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정", description = "하나의 게시글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<PostResponse>> updatePostById(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(id, postUpdateRequest);
        PostResponse postResponse = postService.findPostById(id);

        return success(postResponse, HttpStatus.OK);
    }

    @Operation(summary = "게시글 삭제", description = "하나의 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Object>> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);

        return success(null, HttpStatus.NO_CONTENT);
    }
}
