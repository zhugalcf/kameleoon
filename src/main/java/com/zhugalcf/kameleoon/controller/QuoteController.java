package com.zhugalcf.kameleoon.controller;

import com.zhugalcf.kameleoon.dto.QuoteDto;
import com.zhugalcf.kameleoon.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quote")
@Slf4j
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping
    public void createQuote(@RequestBody @Valid QuoteDto quoteDto) {
        quoteService.createQuote(quoteDto);
    }

    @GetMapping("/random")
    public QuoteDto getRandomQuote() {
        return quoteService.getRandomQuote();
    }

    @GetMapping("/last")
    public List<QuoteDto> getLastQuotes() {
        return quoteService.getLastQuotes();
    }

    @GetMapping("/top")
    public List<QuoteDto> getTopQuotes() {
        return quoteService.getTopQuotes();
    }

    @GetMapping("/flop")
    public List<QuoteDto> getFlopQuotes() {
        return quoteService.getFlopQuotes();
    }

    @PatchMapping
    public void updateQuote(@RequestBody @Valid QuoteDto quoteDto) {
        quoteService.updateQuote(quoteDto);
    }

    @DeleteMapping("/{id}")
    public void deleteQuote(@PathVariable("id") long quoteId) {
        quoteService.deleteQuote(quoteId);
    }

    @PutMapping("/{userId}/user/{voteId}/vote")
    public void vote(@PathVariable("voteId") long quoteId, @PathVariable("userId") long userId) {
        quoteService.vote(quoteId, userId);
    }

    @PutMapping("/{userId}/user/{voteId}/un-vote")
    public void unVote(@PathVariable("voteId") long quoteId, @PathVariable("userId") long userId) {
        quoteService.unVote(quoteId, userId);
    }
}
