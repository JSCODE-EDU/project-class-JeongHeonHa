package com.bulletinboard.member.domain.vo;

import com.bulletinboard.member.exception.InvalidMemberEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class EmailTest {

    @DisplayName("이메일은 @을 하나만 가질 수 있다.")
    @Test
    void Email_Valid_Ok() {
        //given
        String email = "aaaa1111@gmail.com";

        //when
        Email result = Email.of(email);

        //then
        assertThat(result.getEmail()).isEqualTo(email);
    }

    @DisplayName("이메일은 @가 2개 이상이면 예외를 발생시킨다.")
    @CsvSource(value = {"a@aaa111@gmail.com", "aaaa111@@gmail.com", "aaaa111@gmail@.com", "aaaa111@gmail.co@"})
    @ParameterizedTest
    void Email_Valid_Ex(String email) {

        //when //then
        assertThatThrownBy(() -> Email.of(email))
                        .isInstanceOf(InvalidMemberEmailException.class);
    }
}