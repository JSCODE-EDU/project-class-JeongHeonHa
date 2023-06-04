package com.auth.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Authority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected Authority() {
    }

    public Authority(String an)
}
