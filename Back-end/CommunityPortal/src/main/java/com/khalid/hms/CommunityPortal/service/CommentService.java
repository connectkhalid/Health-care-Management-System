package com.khalid.hms.CommunityPortal.service;

import com.khalid.hms.CommunityPortal.dto.CommentDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto) throws CustomException;
    CommentDto updateComment(Long commentId, CommentDto commentDto) throws CustomException;
    List<CommentDto> getAllCommentsForPost(Long postId);
    CommentDto getCommentById(Long commentId) throws CustomException;
}
