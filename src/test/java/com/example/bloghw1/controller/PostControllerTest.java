package com.example.bloghw1.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bloghw1.PostSetup;
import com.example.bloghw1.dto.PostRequestDTO;
import com.example.bloghw1.entity.Post;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostSetup postSetup;

    @Test
    @DisplayName("게시글 작성")
    void createPost() throws Exception {
        PostRequestDTO postRequestDTO = new PostRequestDTO("title", "contents", "author", "password");

        String request = objectMapper.writeValueAsString(postRequestDTO);
        mockMvc.perform(post("/api/posts")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("title"))
            .andExpect(jsonPath("$.contents").value("contents"))
            .andExpect(jsonPath("$.author").value("author"))
            .andDo(print());
    }
    @DisplayName("게시글 단건 조회")
    @Test
    void getPost() throws Exception {
        Post post = Post.builder()
            .title("test1")
            .contents("test1")
            .author("test1")
            .password("test1")
            .build();
        Long postId = postSetup.savePost(post);

        mockMvc.perform(get("/api/posts/{postId}",postId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.postId").value(postId))
            .andExpect(jsonPath("$.title").value(post.getTitle()))
            .andExpect(jsonPath("$.contents").value(post.getContents()))
            .andExpect(jsonPath("$.author").value(post.getAuthor()))
            .andDo(print());
    }
    @DisplayName("게시글 목록 조회")
    @Test
    void getPosts() throws Exception {
        Post post1 = Post.builder()
            .title("test1")
            .contents("test1")
            .author("test1")
            .password("test1")
            .build();
        Post post2 = Post.builder()
            .title("test2")
            .contents("test2")
            .author("test2")
            .password("test2")
            .build();
        Long post1Id = postSetup.savePost(post1);
        Long post2Id = postSetup.savePost(post2);

        mockMvc.perform(get("/api/posts")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].postId").value(post2Id))
            .andExpect(jsonPath("$[1].postId").value(post1Id))
            .andDo(print());
    }

    @DisplayName("게시글 수정")
    @Test
    void modifyPost() throws Exception {
        Post post1 = Post.builder()
            .title("test1")
            .contents("test1")
            .author("test1")
            .password("test1")
            .build();

        Long savedPostId = postSetup.savePost(post1);
        String inputPassword = "test1";
        PostRequestDTO requestDTO = new PostRequestDTO("modifyTest1","modifyTest1","modifyTest1",inputPassword);
        String request = objectMapper.writeValueAsString(requestDTO);

        mockMvc.perform(put("/api/posts/{postId}",savedPostId)
            .content(request)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("modifyTest1"))
            .andExpect(jsonPath("$.contents").value("modifyTest1"))
            .andExpect(jsonPath("$.author").value("modifyTest1"))
            .andDo(print());
    }

    @DisplayName("게시글 삭제")
    @Test
    void deletePost() throws Exception {
        Post post1 = Post.builder()
            .title("test1")
            .contents("test1")
            .author("test1")
            .password("test1")
            .build();
        Long savedPostId = postSetup.savePost(post1);
        String inputPassword = "test1";
        Map<String, String> map = new HashMap<>();
        map.put("password",inputPassword);
        String request = objectMapper.writeValueAsString(map);

        mockMvc.perform(delete("/api/posts/{postId}",savedPostId)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value("true"))
            .andDo(print());
    }
}
