package com.bulletinboard.post.domain.vo;

import com.bulletinboard.post.exception.InvalidTitleException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TitleTest {

    @DisplayName("제목을 저장한다.")
    @Test
    void createTitle() {
        //given
        String title = "e".repeat(15);

        //when
        Title result = Title.from(title);

        //then
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
    }

    @DisplayName("제목은 15글자가 넘어가면 예외가 발생한다.")
    @Test
    void createTitle_Over_Length_Ex() {
        //given
        String title = "e".repeat(16);

        //when //then
        Assertions.assertThatThrownBy(() -> Title.from(title))
                .isInstanceOf(InvalidTitleException.class);
    }
}
