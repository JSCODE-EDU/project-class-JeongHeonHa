package com.bulletinboard.post.repository;

import com.bulletinboard.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired PostRepository postRepository;

    private Post createPost() {
        return Post.PostBuilder.aPost()
                .title("title")
                .content("content")
                .build();
    }

    @Test
    @DisplayName("post를 저장한다.")
    void saveTest() {
        //given
        Post post = createPost();

        //when
        Post result = postRepository.save(post);

        //then
        assertThat(result).isSameAs(post);
    }

    @Test
    @DisplayName("post를 반환한다.")
    void findByIdTest() {
        //given
        Post post = createPost();

        //when
        postRepository.save(post);
        Optional<Post> result = postRepository.findById(post.getId());

        //then
        assertThat(result.get()).isEqualTo(post);
    }

    @Test
    @DisplayName("0번째 페이지 부터 2개의 post를 반환한다.")
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

    @Test
    @DisplayName("post를 제거한다.")
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

    @Test
    @DisplayName("제목에 keyword를 포함한 모든 post를 반환한다.")
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