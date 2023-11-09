package com.sourcery.oirs.service;

import com.sourcery.oirs.database.repository.IssueRepository;
import com.sourcery.oirs.dto.response.IsVotedResponseDto;
import com.sourcery.oirs.dto.response.VoteCountResponseDto;
import com.sourcery.oirs.dto.response.VoteResponseDto;
import com.sourcery.oirs.model.Vote;
import com.sourcery.oirs.database.repository.VoteRepository;
import com.sourcery.oirs.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteService {
    private static final String ISSUE_NOT_FOUND = "Vote with %s id not found";

    private final VoteRepository _voteRepository;
    private final IssueRepository _issueRepository;
    private final UserRepository _userRepository;


    @Transactional
    public VoteResponseDto CreateVote(UUID issueId, UUID employeeId) {
        if(!_issueRepository.findById(issueId).isPresent()){
            return null;
        }

        if(!_userRepository.findById(employeeId).isPresent()){
            return null;
        }

        Vote vote = Vote.builder()
                .id(UUID.randomUUID())
                .issueId(issueId)
                .employeeId(employeeId)
                .build();{
        _voteRepository.insert(vote);
        return VoteResponseDto.of(vote);
                }
    }

    @Transactional
    public IsVotedResponseDto IsVoted(UUID issueId, UUID employeeId) {
        Optional<VoteResponseDto> voteDto = _voteRepository.GetVote(issueId, employeeId);
        IsVotedResponseDto responseDto = IsVotedResponseDto.builder()
                .isVoted(voteDto.isPresent())
                .build();
        return responseDto;
    }

    @Transactional
    public VoteCountResponseDto VoteCount(UUID issueId) {
        VoteCountResponseDto voteCountDto = _voteRepository.GetVoteCount(issueId);
        return voteCountDto;
    }

    @Transactional
    public void DeleteVote(UUID issueId, UUID employeeId) {
        _voteRepository.DeleteVote(issueId, employeeId);
    }
}
