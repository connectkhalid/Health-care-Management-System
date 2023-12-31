package com.khalid.hms.CommunityPortal.service.serviceImpl;

import com.khalid.hms.CommunityPortal.dto.UserDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.feignclient.UserClient;
import com.khalid.hms.CommunityPortal.repository.PostRepository;
import com.khalid.hms.CommunityPortal.dto.PostDto;
import com.khalid.hms.CommunityPortal.entity.PostEntity;
import com.khalid.hms.CommunityPortal.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;
    @Override
    public PostDto createPost(PostDto postDto) throws CustomException {
        UserDto user = userClient.getPatientProfileByToken();
        PostEntity postEntity = new PostEntity();
        if(user != null)
        {
            postEntity.setPostTime(LocalDateTime.now());
            postEntity.setCategory(postDto.getCategory());
            postEntity.setPostTitle(postDto.getPostTitle());
            postEntity.setPostContent(postDto.getPostContent());
            postEntity.setPatientId(user.getId());
            PostEntity savedPostEntity = postRepository.save(postEntity);
            return modelMapper.map(savedPostEntity, PostDto.class);
        }
        else {
            throw new CustomException("User doesn't exist");
        }
    }
    @Override
    public PostDto updatePost(Long postId, PostDto updatedPostDto) throws CustomException {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);
        UserDto user = userClient.getPatientProfileByToken();

        if (optionalPostEntity.isPresent()) {
            PostEntity existingPostEntity = optionalPostEntity.get();
            existingPostEntity.setPostTitle(updatedPostDto.getPostTitle());
            existingPostEntity.setPostContent(updatedPostDto.getPostContent());
            existingPostEntity.setPostTime(LocalDateTime.now());
            existingPostEntity.setPatientId(user.getId());
            PostEntity updatedPostEntity = postRepository.save(existingPostEntity);
            return modelMapper.map(updatedPostEntity, PostDto.class);
        } else {
            throw new CustomException("Post not found with ID: " + postId);
        }
    }

    @Override
    public PostDto getPostById(Long postId) throws CustomException {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        if (optionalPostEntity.isPresent()) {
            return modelMapper.map(optionalPostEntity.get(), PostDto.class);
        } else {
            throw new CustomException("Post not found with ID: " + postId);
        }
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }
}
