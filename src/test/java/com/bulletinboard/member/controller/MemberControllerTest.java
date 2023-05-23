package com.bulletinboard.member.controller;

import com.bulletinboard.ControllerTestSupport;
import com.bulletinboard.member.dto.MemberNewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    @DisplayName("회원 가입을 한다.")
    @Test
    void signUp() throws Exception {
        //given
        MemberNewRequest request = createMemberNewRequest("email1111@gmail.com", "password");

        given(memberService.saveMember(request)).willReturn(1L);

        //when
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @DisplayName("이메일이 비어있으면 예외를 발생시킨다.")
    @Test
    void email_Blank_Ex() throws Exception {
        //given
        MemberNewRequest request = createMemberNewRequest("", "password");

        //when //then
        mockMvc.perform(post("/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @DisplayName("비밀번호가 비어있으면 예외를 발생시킨다.")
    @Test
    void password_Blank_Ex() throws Exception {
        //given
        MemberNewRequest request = createMemberNewRequest("email1111@gmail.com", "");

        //when //then
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    private MemberNewRequest createMemberNewRequest(String email, String password) {
        return MemberNewRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

}