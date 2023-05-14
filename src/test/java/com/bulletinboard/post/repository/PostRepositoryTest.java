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
    @DisplayName("save 메서드는 Post 객체를 저장한다.")
    void saveTest() {
        //given
        Post post = createPost();

        //when
        Post result = postRepository.save(post);

        //then
        assertThat(result).isSameAs(post);
    }

    @Test
    @DisplayName("findById 메서드는 id를 제공하면 id에 맞는 Post를 반환한다.")
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
    @DisplayName("findAll 메서드는 0번째 페이지 부터 2개의 post를 반환한다.")
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
    @DisplayName("deleteById 메서드는 id에 맞는 Post를 제거한다.")
    void deleteByIdTest() {
        //given
        Post post = createPost();

        //when
        postRepository.save(post);
        postRepository.deleteById(post.getId());
        Optional<Post> result = postRepository.findById(post.getId());

        //then
        assertThat(result.isEmpty()).isTrue();
    }

}