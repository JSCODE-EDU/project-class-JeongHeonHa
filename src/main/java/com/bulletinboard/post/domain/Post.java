package com.bulletinboard.post.domain;

import com.bulletinboard.common.BaseTimeEntity;
import com.bulletinboard.post.domain.vo.Content;
import com.bulletinboard.post.domain.vo.Title;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Title title;

    private Content content;

    protected Post() {
    }

    public String getTitleValue() {
        return title.getTitle();
    }

    public String getContentValue() {
        return content.getContent();
    }

    public void updateTitle(String title) {
        this.title = Title.from(title);
    }

    public void updateContent(String content) {
        this.content = Content.from(content);
    }

    public static final class PostBuilder {
        private Title title;
        private Content content;

        private PostBuilder() {
        }

        public static PostBuilder aPost() {
            return new PostBuilder();
        }

        public PostBuilder title(String title) {
            this.title = Title.from(title);
            return this;
        }

        public PostBuilder content(String content) {
            this.content = Content.from(content);
            return this;
        }

        public Post build() {
            Post post = new Post();
            post.title = this.title;
            post.content = this.content;
            return post;
        }
    }
}
