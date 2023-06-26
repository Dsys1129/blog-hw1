package com.example.bloghw1.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.example.bloghw1.Exception.PasswordMismatchException;
import com.example.bloghw1.dto.PostRequestDTO;
import com.example.bloghw1.dto.PostResponseDTO;
import com.example.bloghw1.entity.Post;
import com.example.bloghw1.repository.PostRepository;

class PostServiceImplTest {

    private PostRepository postRepository = Mockito.mock(PostRepository.class);
    private PostServiceImpl postService;

    @BeforeEach
    public void setUpTest(){
        postService = new PostServiceImpl(postRepository);
    }

    @DisplayName("단건 조회 단위 테스트")
    @Test
    void getPostTest(){
        //given
        PostRequestDTO postRequestDTO = new PostRequestDTO("title1","contents1","author1","password1");
        Post givenPost = postRequestDTO.toEntity();

        // PostRepository의 동작에 대한 결괏값 리턴설정
        Mockito.when(postRepository.findById(1L))
            .thenReturn(Optional.of(givenPost));

        //when
        // 메서드를 호출해서 동작 테스트
        PostResponseDTO postResponseDTO = postService.getPost(1L);

        //then
        Assertions.assertThat(postResponseDTO.getPostId()).isEqualTo(givenPost.getPostId());
        Assertions.assertThat(postResponseDTO.getTitle()).isEqualTo(givenPost.getTitle());
        Assertions.assertThat(postResponseDTO.getAuthor()).isEqualTo(givenPost.getAuthor());
        Assertions.assertThat(postResponseDTO.getContents()).isEqualTo(givenPost.getContents());
        Assertions.assertThat(postResponseDTO.getCreatedDate()).isEqualTo(givenPost.getCreatedDate());

        Mockito.verify(postRepository).findById(1L);
    }


    @DisplayName("게시글 저장 단위 테스트")
    @Test
    void savePostTest(){
        // 메서드의 실행만을 확인
        Mockito.when(postRepository.save(ArgumentMatchers.any(Post.class)))
            .then(AdditionalAnswers.returnsFirstArg());

        PostResponseDTO postResponseDTO = postService.createPost(new PostRequestDTO("title", "contents", "author", "password"));
        Assertions.assertThat(postResponseDTO.getTitle()).isEqualTo("title");
        Assertions.assertThat(postResponseDTO.getContents()).isEqualTo("contents");
        Assertions.assertThat(postResponseDTO.getAuthor()).isEqualTo("author");

        Mockito.verify(postRepository).save(ArgumentMatchers.any());
    }

    @DisplayName("게시글 수정 단위 테스트_성공")
    @Test
    void modifyPostTest_O(){
        PostRequestDTO postRequestDTO = new PostRequestDTO("title1","contents1","author1","password1");
        Post givenPost = postRequestDTO.toEntity();

        Mockito.when(postRepository.findById(1L))
            .thenReturn(Optional.of(givenPost));

        String newPassword = "password1";
        PostRequestDTO modifiedPostRequestDTO = new PostRequestDTO("modifiedTitle1", "modifiedContents1", "modifiedAuthor1", newPassword);
        PostResponseDTO modifiedResponse = postService.modifyPost(1L, modifiedPostRequestDTO);

        Mockito.verify(postRepository).findById(1L);
        Assertions.assertThat(givenPost.getTitle()).isEqualTo(modifiedResponse.getTitle());
        Assertions.assertThat(givenPost.getContents()).isEqualTo(modifiedResponse.getContents());
        Assertions.assertThat(givenPost.getAuthor()).isEqualTo(modifiedResponse.getAuthor());
    }

    @DisplayName("게시글 수정 단위 테스트_실패")
    @Test
    void modifyPostTest_X(){
        PostRequestDTO postRequestDTO = new PostRequestDTO("title1","contents1","author1","password1");
        Post givenPost = postRequestDTO.toEntity();

        Mockito.when(postRepository.findById(1L))
            .thenReturn(Optional.of(givenPost));

        String newPassword = "passwordMismatch";
        PostRequestDTO modifiedPostRequestDTO = new PostRequestDTO("modifiedTitle1", "modifiedContents1", "modifiedAuthor1", newPassword);
        assertThrows(PasswordMismatchException.class, () -> {
                postService.modifyPost(1L, modifiedPostRequestDTO);
            });
        Mockito.verify(postRepository).findById(1L);
        Assertions.assertThat(givenPost.getTitle()).isNotEqualTo(modifiedPostRequestDTO.getTitle());
        Assertions.assertThat(givenPost.getContents()).isNotEqualTo(modifiedPostRequestDTO.getContents());
        Assertions.assertThat(givenPost.getAuthor()).isNotEqualTo(modifiedPostRequestDTO.getAuthor());
    }
    @DisplayName("게시글 삭제 단위 테스트_성공")
    @Test
    void deletePost() {
        Post givenPost = Post.builder()
            .title("title")
            .contents("contents")
            .author("author")
            .password("password")
            .build();
        Mockito.when(postRepository.findById(1L))
            .thenReturn(Optional.of(givenPost));

        Long deletePostId = 1L;
        String password = "password";

        postService.deletePost(deletePostId,password);

        // Mockito.verify(postRepository).findById(deletePostId);

        Mockito.when(postRepository.findById(deletePostId))
            .thenReturn(Optional.empty());
        Optional<Post> deletedPost = postRepository.findById(deletePostId);
        assertFalse(deletedPost.isPresent());

    }

    @DisplayName("게시글 삭제 테스트_실패")
    @Test
    void deletePost_X(){
        Post givenPost = Post.builder()
            .title("title")
            .contents("contents")
            .author("author")
            .password("password")
            .build();
        Mockito.when(postRepository.findById(1L))
            .thenReturn(Optional.of(givenPost));

        Long deletePostId = 1L;
        String wrongPassword = "passwordMismatch";

        assertThrows(PasswordMismatchException.class, () -> {
            postService.deletePost(deletePostId,wrongPassword);
        });

        Mockito.when(postRepository.findById(deletePostId)).thenReturn(Optional.empty());
        Optional<Post> findPost = postRepository.findById(deletePostId);
        assertTrue(findPost.isEmpty());

    }
}
