package com.bulletinboard.member.domain;

import com.bulletinboard.common.BaseTimeEntity;
import com.bulletinboard.member.domain.vo.Email;
import com.bulletinboard.member.domain.vo.Password;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Email email;

    private Password password;

    protected Member() {
    }

    @Builder
    private Member(String email, String password) {
        this.email = Email.of(email);
        this.password = Password.of(password);
    }

    public String getEmailValue() {
        return email.getEmail();
    }

    public String getPasswordValue() {
        return password.getPassword();
    }
}
