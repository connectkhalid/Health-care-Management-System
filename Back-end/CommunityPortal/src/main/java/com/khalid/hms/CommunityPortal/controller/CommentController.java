package com.khalid.hms.CommunityPortal.controller;

import com.khalid.hms.CommunityPortal.dto.CommentDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/createComment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto CommentDto) throws CustomException {
        CommentDto comment = commentService.createComment(CommentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
    @GetMapping("/getAllComments/{postId}")
    public ResponseEntity<List<CommentDto>> getComment(@PathVariable("postId") Long postId) throws CustomException {
        List<CommentDto> comment = commentService.getAllCommentsForPost(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
}
