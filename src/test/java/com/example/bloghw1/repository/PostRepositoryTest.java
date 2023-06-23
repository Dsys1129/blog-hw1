package com.example.bloghw1.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.bloghw1.entity.Post;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName(value = "생성 기준 내림차순으로 가져온다.")
    @Test
    void findAllPosts() throws InterruptedException {

        Post post1 = Post.builder()
            .title("1")
            .contents("1")
            .author("1")
            .password("1")
            .build();
        Post savedPost1 = postRepository.save(post1);

        Thread.sleep(3000);

        Post post2 = Post.builder()
            .title("2")
            .contents("2")
            .author("2")
            .password("2")
            .build();
        Post savedPost2 = postRepository.save(post2);

        List<Post> posts =
            postRepository.findAllByOrderByCreatedDateDesc();

        Assertions.assertThat(posts.get(0) == savedPost2).isTrue();
    }
}
