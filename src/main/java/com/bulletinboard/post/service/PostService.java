package com.bulletinboard.post.service;

import com.bulletinboard.post.domain.Post;
import com.bulletinboard.post.dto.PostNewRequest;
import com.bulletinboard.post.dto.PostResponse;
import com.bulletinboard.post.dto.PostUpdateRequest;
import com.bulletinboard.post.dto.PostUpdateResponse;
import com.bulletinboard.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long savePost(PostNewRequest postNewRequest) {
        Post post = PostAssembler.toEntity(postNewRequest);
        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    public List<PostResponse> findAllPosts() {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new IllegalStateException("게시글이 없습니다.");
        }

        return posts.stream()
                .map(PostAssembler::toDto)
                .collect(Collectors.toList());
    }

    public PostResponse findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        return PostAssembler.toDto(post);
    }

    public PostUpdateResponse findUpdatedPostById(Long id) {
        Post updatedPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        return PostAssembler.toUpdateDto(updatedPost);
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequest postUpdateRequest) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        findPost.updateTitle(postUpdateRequest.getTitle());
        findPost.updateContent(postUpdateRequest.getContent());
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
