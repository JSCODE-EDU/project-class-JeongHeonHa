package com.bulletinboard.member.dto;

import com.bulletinboard.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberNewRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    private MemberNewRequest() {
    }

    @Builder
    private MemberNewRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity(MemberNewRequest memberNewRequest) {
        return Member.builder()
                .email(memberNewRequest.getEmail())
                .password(memberNewRequest.getPassword())
                .build();
    }
}
