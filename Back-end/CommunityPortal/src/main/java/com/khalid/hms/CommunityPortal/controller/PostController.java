package com.khalid.hms.CommunityPortal.controller;

import com.khalid.hms.CommunityPortal.dto.PostDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/createPost")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) throws CustomException {
        PostDto post = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto updatedPostDto) throws CustomException {
        PostDto updatedPost = postService.updatePost(postId, updatedPostDto);
        return ResponseEntity.ok(updatedPost);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) throws CustomException {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/allPost")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}
