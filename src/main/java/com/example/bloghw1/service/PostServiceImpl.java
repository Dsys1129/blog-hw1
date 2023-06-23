package com.example.bloghw1.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bloghw1.dto.PostRequestDTO;
import com.example.bloghw1.dto.PostResponseDTO;
import com.example.bloghw1.entity.Post;
import com.example.bloghw1.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Transactional
    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {
        Post post = postRequestDTO.toEntity();
        Post savedPost = postRepository.save(post);

        PostResponseDTO response = new PostResponseDTO(savedPost);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDTO> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedDateDesc();

        List<PostResponseDTO> response = posts.stream()
            .map(PostResponseDTO::new)
            .collect(Collectors.toList());
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostResponseDTO response = new PostResponseDTO(post);
        return response;
    }

    @Transactional
    @Override
    public PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (!post.getPassword().equals(postRequestDTO.getPassword())) {
            post.modifyPost(postRequestDTO.getTitle(), postRequestDTO.getAuthor(), postRequestDTO.getContents());
        }
        PostResponseDTO response = new PostResponseDTO(post);
        return response;
    }

    @Transactional
    @Override
    public ResponseEntity deletePost(Long postId, String password) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (!post.getPassword().equals(password)) {
            return ResponseEntity.ok(Collections.singletonMap("success","false"));
        }
        postRepository.delete(post);
        return ResponseEntity.ok(Collections.singletonMap("success","true"));
    }
}
