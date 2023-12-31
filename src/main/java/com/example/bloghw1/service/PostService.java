package com.example.bloghw1.service;

import java.util.List;
import java.util.Map;

import com.example.bloghw1.dto.PostRequestDTO;
import com.example.bloghw1.dto.PostResponseDTO;

public interface PostService {

    PostResponseDTO createPost(PostRequestDTO postRequestDTO);

    List<PostResponseDTO> getPosts();

    PostResponseDTO getPost(Long postId);

    PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO);

    Map<String,String> deletePost(Long postId, String password);
}
