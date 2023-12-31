package com.khalid.hms.CommunityPortal.service;

import com.khalid.hms.CommunityPortal.dto.VoteCountDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.dto.VoteDto;

public interface VoteService {
    void castVote(VoteDto voteDto) throws CustomException;
    VoteCountDto countVotes(Long postId) throws CustomException;
}
