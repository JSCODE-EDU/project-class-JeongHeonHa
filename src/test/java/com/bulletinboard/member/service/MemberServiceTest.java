package com.bulletinboard.member.service;

import com.bulletinboard.IntegrationTestSupport;
import com.bulletinboard.member.dto.MemberNewRequest;
import com.bulletinboard.member.exception.InvalidMemberEmailException;
import com.bulletinboard.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("회원을 회원목록에 저장한다.")
    @Test
    void saveMember() {
        //given
        MemberNewRequest request = createMemberNewRequest("email1111@gmail.com", "password");

        //when
        Long savedId = memberService.saveMember(request);

        //then
        Assertions.assertThat(savedId).isNotZero();
    }

    @DisplayName("중복된 이메일이 있다면 예외가 발생한다.")
    @Test
    void saveMember_Duplicate_Email_Ex() {
        //given
        MemberNewRequest request = createMemberNewRequest("email1111@gmail.com", "password");
        memberService.saveMember(request);
        MemberNewRequest request2 = createMemberNewRequest("email1111@gmail.com", "password");

        //when //then
        Assertions.assertThatThrownBy(() -> memberService.saveMember(request2))
                .isInstanceOf(InvalidMemberEmailException.class);
    }

    private MemberNewRequest createMemberNewRequest(String email, String password) {
        return MemberNewRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}