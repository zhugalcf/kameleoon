package com.zhugalcf.kameleoon.service;

import com.zhugalcf.kameleoon.dto.VoteDto;
import com.zhugalcf.kameleoon.mapper.VoteMapper;
import com.zhugalcf.kameleoon.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    public List<VoteDto> getAllQuoteVotes(long quoteId) {
        return voteRepository.findAllQuoteVotes(quoteId).stream()
                .map(voteMapper::toDto)
                .toList();
    }
}
