package com.khalid.hms.CommunityPortal.controller;

import com.khalid.hms.CommunityPortal.dto.VoteCountDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.dto.VoteDto;
import com.khalid.hms.CommunityPortal.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;
    @PostMapping("/cast")
    public ResponseEntity<String> castVote(@RequestBody VoteDto voteDto) {
        try {
            voteService.castVote(voteDto);
            return ResponseEntity.ok("Vote cast successfully.");
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
        }
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<VoteCountDto> countVotes(@PathVariable Long postId) {
        try {
            VoteCountDto voteCountDto = voteService.countVotes(postId);
            return ResponseEntity.ok(voteCountDto);
        } catch (CustomException ex) {
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }
}