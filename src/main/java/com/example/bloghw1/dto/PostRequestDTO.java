package com.example.bloghw1.dto;

import com.example.bloghw1.entity.Post;

import lombok.Getter;

@Getter
public class PostRequestDTO {

    private String title;

    private String contents;

    private String author;

    private String password;

    public Post toEntity(){
        return Post.builder()
            .title(title)
            .contents(contents)
            .author(author)
            .password(password)
            .build();
    }
}
