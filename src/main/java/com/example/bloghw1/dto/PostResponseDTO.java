package com.example.bloghw1.dto;

import java.time.LocalDateTime;

import com.example.bloghw1.entity.Post;

import lombok.Getter;

@Getter
public class PostResponseDTO {

    private final Long postId;

    private final String title;

    private final String author;

    private final String contents;

    private final LocalDateTime createdDate;

    public PostResponseDTO(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.contents = post.getContents();
        this.createdDate = post.getCreatedDate();
    }
}
