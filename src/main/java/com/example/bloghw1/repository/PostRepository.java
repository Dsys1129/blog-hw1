package com.example.bloghw1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloghw1.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByOrderByCreatedDateDesc();
}
