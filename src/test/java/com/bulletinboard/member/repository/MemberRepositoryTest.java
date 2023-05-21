package com.bulletinboard.member.repository;

import com.bulletinboard.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @DisplayName("멤버를 회원목록에 저장한다.")
    @Test
    void saveMember_Ok() {
        //given
        Member member = createMember("email1111@gamil.com", "password");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Assertions.assertThat(savedMember).isNotNull();
    }

    @DisplayName("동일한 이메일이 존재하면 true를 반환한다.")
    @Test
    void saveMember_SameEmail_Ex() {
        //given
        Member member = createMember("email1111@gamil.com", "password");

        memberRepository.save(member);

        String email = "email1111@gamil.com";

        //when
        boolean result = memberRepository.existsByEmail(email);

        //then
        Assertions.assertThat(result).isTrue();
    }

    private Member createMember(String email, String password) {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}