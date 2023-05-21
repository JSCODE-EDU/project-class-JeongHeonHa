package com.bulletinboard.post.domain.vo;

import com.bulletinboard.post.exception.InvalidContentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ContentTest {

    @DisplayName("내용을 저장한다.")
    @Test
    void createContent() {
        //given
        String content = "e".repeat(1000);

        //when
        Content result = Content.from(content);

        //then
        assertThat(result.getContent()).isEqualTo(content);
    }

    @DisplayName("내용은 1000글자가 넘으면 예외가 발생한다.")
    @Test
    void createContent_Over_Length_Ex() {
        //given
        String content = "e".repeat(1001);

        //when //then
        assertThatThrownBy(() -> Content.from(content))
                .isInstanceOf(InvalidContentException.class);
    }
}