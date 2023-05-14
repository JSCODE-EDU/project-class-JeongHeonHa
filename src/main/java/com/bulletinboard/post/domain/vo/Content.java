package com.bulletinboard.post.domain.vo;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Content {

    @Column(name = "content")
    private String content;

    protected Content() {
    }

    private Content(String content) {
        this.content = content;
    }

    public static Content from(String content) {
        return new Content(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content1 = (Content) o;
        return Objects.equals(content, content1.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
