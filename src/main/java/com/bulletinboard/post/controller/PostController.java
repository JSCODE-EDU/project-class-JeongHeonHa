package com.bulletinboard.post.controller;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.exception.InvalidPostException;
import com.bulletinboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> savePost(@Valid @RequestBody PostNewRequest postNewRequest) {
        Long savedPostId = postService.savePost(postNewRequest);
        PostResponse postResponse = postService.findPostById(savedPostId);

        return ResponseEntity.created(URI.create("/posts")).body(postResponse);
    }

    @GetMapping
    public ResponseEntity<Slice<PostResponse>> findPosts(
            @PageableDefault(size = 100, sort = "createdDate", direction = DESC) Pageable pageable) {

        if (pageable.getPageSize() > 100) {
            throw new InvalidPostException();
        }

        Slice<PostResponse> postResponses = postService.findPosts(pageable);
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/word")
    public ResponseEntity<Slice<PostResponse>> findPostsByKeyword(
            @RequestParam String keyword,
            @PageableDefault(size = 100, sort = "createdDate", direction = DESC) Pageable pageable) {

        if (pageable.getPageSize() > 100) {
            throw new InvalidPostException();
        }

        if (!StringUtils.hasText(keyword)) throw new InvalidPostException("키워드는 필수 입니다.");

        Slice<PostResponse> postResponses = postService.findPostsByKeyword(keyword, pageable);
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.findPostById(id);

        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(id, postUpdateRequest);
        PostResponse postResponse = postService.findPostById(id);

        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);

        return ResponseEntity.noContent().build();
    }
}
