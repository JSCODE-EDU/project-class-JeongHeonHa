package com.bulletinboard.post.controller;

import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.dto.PostUpdateResponse;
import com.bulletinboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> savePost(@RequestBody PostNewRequest postNewRequest) {
        Long savedPostId = postService.savePost(postNewRequest);
        PostResponse postResponse = postService.findById(savedPostId);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> findAllPosts() {
        List<PostResponse> postResponses = postService.findAllPosts();

        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        PostResponse postResponse = postService.findById(id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PostMapping("/posts/edit/{id}")
    public ResponseEntity<PostUpdateResponse> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(id, postUpdateRequest);
        PostUpdateResponse postUpdateResponse = postService.findUpdatedPostById(id);

        return new ResponseEntity<>(postUpdateResponse, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePostById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
