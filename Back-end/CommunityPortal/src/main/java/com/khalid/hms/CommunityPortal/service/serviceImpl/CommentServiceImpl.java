package com.khalid.hms.CommunityPortal.service.serviceImpl;

import com.khalid.hms.CommunityPortal.dto.UserDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.feignclient.UserClient;
import com.khalid.hms.CommunityPortal.repository.CommentRepository;
import com.khalid.hms.CommunityPortal.repository.PostRepository;
import com.khalid.hms.CommunityPortal.dto.CommentDto;
import com.khalid.hms.CommunityPortal.entity.CommentEntity;
import com.khalid.hms.CommunityPortal.entity.PostEntity;
import com.khalid.hms.CommunityPortal.service.CommentService;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;  // Assuming you have a PostRepository
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserClient userClient;

    @Override
    public CommentDto createComment(CommentDto commentDto) throws CustomException {
        UserDto user = userClient.getPatientProfileByToken();
        Optional<PostEntity> optionalPostEntity = postRepository.findById(commentDto.getPostId());
        if (optionalPostEntity.isPresent()) {
            PostEntity postEntity = optionalPostEntity.get();
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setPatientId(user.getId());
            commentEntity.setName(user.getName());
            commentEntity.setCommentContent(commentDto.getCommentContent());
            commentEntity.setCommentTime(LocalDateTime.now());
            commentEntity.setPost(postEntity);
            commentEntity.setIsAvailable(true);
            return convertToDto(commentRepository.save(commentEntity));
        } else {
            throw new CustomException("Post not found with ID: " + commentDto.getPostId());
        }
    }


    @Override
    public CommentDto updateComment(Long commentId, CommentDto commentDto) throws CustomException {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentId);

        if (optionalCommentEntity.isPresent()) {
            CommentEntity existingCommentEntity = optionalCommentEntity.get();
            existingCommentEntity.setCommentContent(commentDto.getCommentContent());
            // Update other fields as needed

            CommentEntity updatedCommentEntity = commentRepository.save(existingCommentEntity);
            return modelMapper.map(updatedCommentEntity, CommentDto.class);
        } else {
            throw new CustomException("Comment not found with ID: " + commentId);
        }
    }
    @Override
    public List<CommentDto> getAllCommentsForPost(Long postId) {
        List<CommentEntity> commentEntities = commentRepository.findAllByPost_PostIdOrderByCommentTime(postId);

        return commentEntities.stream()
                .map(commentEntity -> convertToDto(commentEntity))
                .collect(Collectors.toList());
    }
    @Override
    public CommentDto getCommentById(Long commentId) throws CustomException {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentId);

        if (optionalCommentEntity.isPresent()) {
            return modelMapper.map(optionalCommentEntity.get(), CommentDto.class);
        } else {
            throw new CustomException("Comment not found with ID: " + commentId);
        }
    }
    private CommentDto convertToDto(CommentEntity commentEntity) {
        CommentDto savedCommentDto = new CommentDto();
        savedCommentDto.setCommentId(commentEntity.getCommentId());
        savedCommentDto.setPatientId(commentEntity.getPatientId());
        savedCommentDto.setName(commentEntity.getName());
        savedCommentDto.setPostId(commentEntity.getPost().getPostId());
        savedCommentDto.setCommentContent(commentEntity.getCommentContent());
        savedCommentDto.setCommentTime(commentEntity.getCommentTime());
        savedCommentDto.setIsAvailable(commentEntity.getIsAvailable());
        return savedCommentDto;
    }
}
