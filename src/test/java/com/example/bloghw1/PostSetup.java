package com.example.bloghw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bloghw1.entity.Post;
import com.example.bloghw1.repository.PostRepository;

@Component
public class PostSetup {

    @Autowired
    private PostRepository postRepository;

    public Long savePost(Post post){
        return postRepository.save(post).getPostId();
    }

}
