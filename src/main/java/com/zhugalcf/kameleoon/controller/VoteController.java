package com.zhugalcf.kameleoon.controller;

import com.zhugalcf.kameleoon.dto.VoteDto;
import com.zhugalcf.kameleoon.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vote")
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/{id}")
    public List<VoteDto> getAllQuoteVotes(@PathVariable("id") long quoteId) {
        return voteService.getAllQuoteVotes(quoteId);
    }
}
