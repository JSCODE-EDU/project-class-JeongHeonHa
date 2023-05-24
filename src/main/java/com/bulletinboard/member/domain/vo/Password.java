package com.bulletinboard.member.domain.vo;

import com.bulletinboard.member.exception.InvalidMemberPasswordException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Password {

    private static final int PASSWORD_UNDER_LIMIT = 8;
    private static final int PASSWORD_OVER_LIMIT = 15;

    @Column(name = "password")
    private String value;

    protected Password() {
    }

    private Password(String value) {
        validate(value);
        this.value = value;
    }

    public static Password of(String password) {
        return new Password(password);
    }

    private void validate(String password) {
        int len = password.length();
        if (len < PASSWORD_UNDER_LIMIT || len > PASSWORD_OVER_LIMIT) {
            throw new InvalidMemberPasswordException(
                    "비밀번호는 " +
                    PASSWORD_UNDER_LIMIT + "자리 이상 " +
                    PASSWORD_OVER_LIMIT + "자리 이하" +
                    "여야합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(value, password1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
