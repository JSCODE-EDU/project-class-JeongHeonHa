package com.bulletinboard.member.domain.vo;

import com.bulletinboard.member.exception.InvalidMemberPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PasswordTest {

    @DisplayName("비밀번호는 8자리 이상 15자리 이하여야한다.")
    @CsvSource(value = {"12345678", "123456789123456"})
    @ParameterizedTest
    void password_Valid_Ok(String password) {
        //when
        Password result = Password.of(password);

        //then
        assertThat(result.getValue()).isEqualTo(password);
    }

    @DisplayName("비밀번호는 8자리 이상 15자리 이하여야한다.")
    @CsvSource(value = {"1234567", "1234567891234567"})
    @ParameterizedTest
    void password_valid_Ex(String password) {

        //when //then
        assertThatThrownBy(() -> Password.of(password))
                .isInstanceOf(InvalidMemberPasswordException.class)
                .hasMessage("비밀번호는 8자리 이상 15자리 이하여야합니다.");
    }
}