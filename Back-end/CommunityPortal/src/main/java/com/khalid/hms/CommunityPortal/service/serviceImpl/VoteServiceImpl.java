package com.khalid.hms.CommunityPortal.service.serviceImpl;

import com.khalid.hms.CommunityPortal.dto.UserDto;
import com.khalid.hms.CommunityPortal.dto.VoteCountDto;
import com.khalid.hms.CommunityPortal.exception.CustomException;
import com.khalid.hms.CommunityPortal.feignclient.UserClient;
import com.khalid.hms.CommunityPortal.repository.PostRepository;
import com.khalid.hms.CommunityPortal.repository.VoteRepository;
import com.khalid.hms.CommunityPortal.service.VoteService;
import com.khalid.hms.CommunityPortal.dto.VoteDto;
import com.khalid.hms.CommunityPortal.entity.PostEntity;
import com.khalid.hms.CommunityPortal.entity.VoteEntity;
import com.khalid.hms.CommunityPortal.enums.VoteType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepository postRepository;
    @Override
    public void castVote(VoteDto voteDto) throws CustomException {
            UserDto user = userClient.getPatientProfileByToken();
            if(user == null) {
                throw new CustomException("You are not authorized to vote this post");
            }

            Optional<PostEntity> postEntity = postRepository.findById(voteDto.getPostId());
            if(postEntity.isEmpty()){
                throw new CustomException("Post not found");
            }
            String patientId = user.getId();

            // Check if the user has already voted
            Optional<VoteEntity> existingVote = voteRepository.findByPatientIdAndPostId(patientId, voteDto.getPostId());
            if (existingVote.isPresent()) {
                // If the user has already voted, update the vote if different
                VoteEntity vote = existingVote.get();
                if (!vote.getVoteType().equals(voteDto.getVoteType())) {
                    vote.setVoteType(voteDto.getVoteType());
                    voteRepository.save(vote);
                } else {
                    // If the user has already voted and the vote is same, throw CustomException
                    throw new CustomException("You have already "+vote.getVoteType()+"d this post");
                }
            } else {
                // If the user has not voted, create a new vote
                VoteEntity newVote = new VoteEntity();
                newVote.setPatientId(patientId);
                newVote.setVoteType(voteDto.getVoteType());
                newVote.setPost(postEntity.get());
                //newVote.setActive(true);
               // newVote.setPatientId(patientId);
                voteRepository.save(newVote);
            }

    }

    @Override
    public VoteCountDto countVotes(Long postId) throws CustomException {

            Optional<PostEntity> postEntity = postRepository.findById(postId);
            if(postEntity.isEmpty()){
                throw new CustomException("Post not found");
            }
            Long upVoteCount = voteRepository.countByPostIdAndVoteType(postId, VoteType.Upvote);
            Long downVoteCount = voteRepository.countByPostIdAndVoteType(postId, VoteType.Downvote);

            VoteCountDto voteCountDto = new VoteCountDto();
            voteCountDto.setPostId(postId);
            voteCountDto.setUpVote(upVoteCount);
            voteCountDto.setDownVote(downVoteCount);
            return voteCountDto;

    }
}