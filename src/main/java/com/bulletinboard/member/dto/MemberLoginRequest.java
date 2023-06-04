package com.bulletinboard.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginRequest {

    private String email;
    private String password;

    private MemberLoginRequest() {
    }

    @Builder
    private MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
