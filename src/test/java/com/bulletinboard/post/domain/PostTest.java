package com.bulletinboard.post.domain;

import com.bulletinboard.post.domain.vo.Content;
import com.bulletinboard.post.domain.vo.Title;
import com.bulletinboard.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class PostTest {

    @Autowired
    PostRepository postRepository;

    private Post createPost() {
        return Post.PostBuilder.aPost()
                .title("title")
                .content("content")
                .build();
    }

    @Test
    @DisplayName("getTitleValue 메서드는 Title의 원시 값을 반환한다.")
    void getTitleValueTest() {
        //given
        Post post = createPost();

        //when
        String result = post.getTitleValue();

        //then
        assertThat(result).isEqualTo("title");
    }

    @Test
    @DisplayName("getContentValue 메서드는 Content의 원시 값을 반환한다.")
    void getContentValueTest() {
        //given
        Post post = createPost();

        //when
        String result = post.getContentValue();

        //then
        assertThat(result).isEqualTo("content");
    }

    @Test
    @DisplayName("updateTitle 메서드는 Title을 변경한다.")
    void updateTitleTest() {
        //given
        Post post = createPost();

        //when
        post.updateTitle("updatedTitle");

        //then
        assertThat(post.getTitleValue()).isEqualTo("updatedTitle");
    }

    @Test
    @DisplayName("updateContent 메서드는 Content를 변경한다.")
    void updateContentTest() {
        //given
        Post post = createPost();

        //when
        post.updateContent("updatedContent");

        //then
        assertThat(post.getContentValue()).isEqualTo("updatedContent");
    }

    @Test
    @DisplayName("getId 메서드는 id를 반환한다.")
    void getIdTest() {
        //given
        Post post = createPost();

        //when
        Post savedPost = postRepository.save(post);
        Long savedPostId = savedPost.getId();

        //then
        assertThat(savedPostId).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("getTitle 메서드는 Title을 반환한다.")
    void getTitleTest() {
        //given
        Post post = createPost();

        //when
        Title result1 = post.getTitle();
        Title result2 = post.getTitle();

        assertThat(result1).isEqualTo(result2);
    }

    @Test
    @DisplayName("getContent 메서드는 Content를 반환한다.")
    void getContentTest() {
        //given
        Post post = createPost();

        //when
        Content result1 = post.getContent();
        Content result2 = post.getContent();

        assertThat(result1).isEqualTo(result2);
    }
}