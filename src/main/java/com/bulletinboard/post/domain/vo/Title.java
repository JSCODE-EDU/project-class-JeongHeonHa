package com.bulletinboard.post.domain.vo;

import com.bulletinboard.post.exception.InvalidTitleException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Title {

    private static final int LIMIT_LENGTH = 15;

    @Column(name = "title")
    private String title;

    protected Title() {
    }

    private Title(String title) {
        validate(title);
        this.title = title;
    }

    public static Title from(String title) {
        return new Title(title);
    }

    private void validate(String title) {
        if (title.length() > LIMIT_LENGTH) {
            throw new InvalidTitleException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title1 = (Title) o;
        return Objects.equals(title, title1.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
