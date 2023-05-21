package com.bulletinboard.member.domain.vo;

import com.bulletinboard.member.exception.InvalidMemberEmailException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Email {

    private static final String EMAIL_FORMAT = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

    @Column(name = "email", unique = true)
    private String email;

    protected Email() {
    }

    private Email(String email) {
        validate(email);
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    private void validate(String email) {
        if (isMisMatch(email)) {
            throw new InvalidMemberEmailException("잘못된 형식의 이메일입니다.");
        }
    }

    private boolean isMisMatch(String email) {
        return !email.matches(EMAIL_FORMAT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
