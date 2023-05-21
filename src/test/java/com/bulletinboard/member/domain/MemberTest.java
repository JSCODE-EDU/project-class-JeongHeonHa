package com.bulletinboard.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("이메일의 원시값을 반환한다.")
    @Test
    void getEmailValue() {
        //given
        Member member = createMember("email1111@gmail.com", "password");

        //when //then
        assertThat(member.getEmailValue()).isEqualTo("email1111@gmail.com");
    }

    @DisplayName("비밀번호의 원시값을 반환한다.")
    @Test
    void getPasswordValue() {
        //given
        Member member = createMember("email1111@gmail.com", "password");

        //when //then
        assertThat(member.getPasswordValue()).isEqualTo("password");
    }

    @DisplayName("이메일 타입의 값을 반환한다.")
    @Test
    void getEmail() {
        //given
        Member member = createMember("email1111@gmail.com", "password");

        //when //then
        assertThat(member.getEmail()).isNotNull();
    }

    @DisplayName("비밀번호 타입의 값을 반환한다.")
    @Test
    void getPassword() {
        //given
        Member member = createMember("email1111@gmail.com", "password");

        //when //then
        assertThat(member.getPassword()).isNotNull();
    }

    private Member createMember(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}