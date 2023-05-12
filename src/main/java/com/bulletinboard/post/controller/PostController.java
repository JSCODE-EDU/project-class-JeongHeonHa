package com.bulletinboard.post.controller;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> savePost(@RequestBody PostNewRequest postNewRequest) {
        Long savedPostId = postService.savePost(postNewRequest);
        PostResponse postResponse = postService.findPostById(savedPostId);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Slice<PostResponse>> findPosts(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @PageableDefault(size = 100, sort = "createdDate", direction = DESC) Pageable pageable) {

        if (pageable.getPageSize() > 100) {
            throw new IllegalArgumentException("페이지는 100개 이상 조회할 수 없습니다");
        }

        if (StringUtils.hasText(keyword)) {
            Slice<PostResponse> postResponses = postService.findPostsByKeyword(keyword, pageable);
            return new ResponseEntity<>(postResponses, HttpStatus.OK);
        }

        Slice<PostResponse> postResponses = postService.findPosts(pageable);
        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.findPostById(id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(id, postUpdateRequest);
        PostResponse postResponse = postService.findPostById(id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
