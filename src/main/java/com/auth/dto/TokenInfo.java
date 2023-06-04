package com.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInfo {

    private String accessToken;

    public TokenInfo(String accessToken) {
        this.accessToken = accessToken;
    }
}
