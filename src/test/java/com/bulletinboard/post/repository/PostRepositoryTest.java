package com.bulletinboard.post.repository;

import com.bulletinboard.IntegrationTestSupport;
import com.bulletinboard.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
class PostRepositoryTest extends IntegrationTestSupport {

    @Autowired PostRepository postRepository;

    private Post createPost() {
        return Post.PostBuilder.aPost()
                .title("title")
                .content("content")
                .build();
    }

    @DisplayName("게시글을 저장한다.")
    @Test
    void saveTest() {
        //given
        Post post = createPost();

        //when
        Post result = postRepository.save(post);

        //then
        assertThat(result).isSameAs(post);
    }

    @DisplayName("게시글 하나를 반환한다.")
    @Test
    void findByIdTest() {
        //given
        Post post = createPost();

        //when
        postRepository.save(post);
        Optional<Post> result = postRepository.findById(post.getId());

        //then
        assertThat(result.get()).isEqualTo(post);
    }

    @DisplayName("0번째 페이지 부터 2개의 게시글을 반환한다.")
    @Test
    void findAllTest() {
        //given
        for (int i=0; i < 2; i++) {
            postRepository.save(createPost());
        }

        int page = 0;
        int size = 2;
        PageRequest pageable = PageRequest.of(page, size);

        //when
        Slice<Post> posts = postRepository.findAll(pageable);

        //then
        assertThat(posts.getSize()).isEqualTo(2);
    }

    @DisplayName("게시글을 제거한다.")
    @Test
    void deleteByIdTest() {
        //given
        Post post = createPost();
        postRepository.save(post);

        //when
        postRepository.deleteById(post.getId());
        Optional<Post> result = postRepository.findById(post.getId());

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @DisplayName("제목에 키워드를 포함한 모든 게시글을 반환한다.")
    @Test
    void findByTitleContaining() {
        //given
        for (int i=1; i < 11; i++) {
            Post post = Post.PostBuilder.aPost()
                    .title("" + i)
                    .build();

            postRepository.save(post);
        }

        //when
        Slice<Post> result = postRepository.findByTitleTitleContaining("1", null);

        //then
        assertThat(result.getSize()).isEqualTo(2);
    }
}