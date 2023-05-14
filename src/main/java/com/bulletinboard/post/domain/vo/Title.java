package com.bulletinboard.post.domain.vo;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Title {

    @Column(name = "title")
    private String title;

    protected Title() {
    }

    private Title(String title) {
        this.title = title;
    }

    public static Title from(String title) {
        return new Title(title);
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
