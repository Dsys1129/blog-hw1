package com.example.bloghw1.service;

import java.util.List;

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

    @Override
    public List<PostResponseDTO> getPosts() {
        return null;
    }

    @Override
    public PostResponseDTO getPost(Long postId) {
        return null;
    }

    @Override
    public PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity deletePost(Long postId, String password) {
        return null;
    }

}
