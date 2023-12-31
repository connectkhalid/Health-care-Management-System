package com.khalid.hms.CommunityPortal.service;

import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto) throws CustomException;
    PostDto updatePost(Long postId, PostDto updatedPostDto) throws CustomException;
    PostDto getPostById(Long postId) throws CustomException;
    List<PostDto> getAllPosts();
}
