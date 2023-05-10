package com.bulletinboard.post.repository;

import com.bulletinboard.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @DisplayName("findAll 메서드는 모든 Post를 반환한다.")
    void findAllTest() {
        //given
        Post post1 = createPost();
        Post post2 = createPost();
        Post post3 = createPost();
        Post post4 = createPost();

        //when
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        //then
        List<Post> posts = postRepository.findAll();

        assertThat(posts.size()).isEqualTo(4);
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