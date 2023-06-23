package com.example.bloghw1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloghw1.dto.PostRequestDTO;
import com.example.bloghw1.dto.PostResponseDTO;
import com.example.bloghw1.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostRequestDTO request) {
        PostResponseDTO response = postService.createPost(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts")
    public ResponseEntity getPosts() {
        List<PostResponseDTO> posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity getPost(@PathVariable("postId") Long postId) {
        PostResponseDTO response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity modifyPost(@PathVariable("postId") Long postId, @RequestBody PostRequestDTO request) {
        PostResponseDTO response = postService.modifyPost(postId, request);
        return ResponseEntity.ok(response);
    }
}
