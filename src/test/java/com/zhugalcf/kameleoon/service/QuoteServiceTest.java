package com.zhugalcf.kameleoon.service;

import com.zhugalcf.kameleoon.dto.QuoteDto;
import com.zhugalcf.kameleoon.entity.Quote;
import com.zhugalcf.kameleoon.entity.User;
import com.zhugalcf.kameleoon.entity.Vote;
import com.zhugalcf.kameleoon.entity.VoteType;
import com.zhugalcf.kameleoon.exception.EntityNotFoundException;
import com.zhugalcf.kameleoon.mapper.QuoteMapper;
import com.zhugalcf.kameleoon.mapper.QuoteMapperImpl;
import com.zhugalcf.kameleoon.repository.QuoteRepository;
import com.zhugalcf.kameleoon.repository.VoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {

    @InjectMocks
    QuoteService quoteService;
    @Mock
    QuoteRepository quoteRepository;
    @Spy
    QuoteMapper quoteMapper = new QuoteMapperImpl();
    @Mock
    VoteRepository voteRepository;
    QuoteDto quoteDto;
    Quote quote;
    Quote quote1;
    Quote quote2;
    Quote quote3;

    @BeforeEach
    void setUp() {
        quoteDto = QuoteDto.builder()
                .id(1L)
                .content("Sample content")
                .userId(1L)
                .build();
        User user = User.builder()
                .id(1L)
                .build();
        quote = Quote.builder()
                .content("Sample content")
                .id(1L)
                .user(user)
                .score(0L)
                .version(0)
                .build();
        quote1 = Quote.builder()
                .content("Sample content")
                .id(1L)
                .user(user)
                .score(5L)
                .version(0)
                .build();
        quote2 = Quote.builder()
                .content("Sample content")
                .id(1L)
                .user(user)
                .score(10L)
                .version(0)
                .build();
        quote3 = Quote.builder()
                .content("Sample content")
                .id(1L)
                .user(user)
                .score(15L)
                .version(0)
                .build();
    }

    @Test
    void testQuoteIsCreated() {
        quoteService.createQuote(quoteDto);
        verify(quoteRepository).save(quote);
    }

    @Test
    void testGetRandomQuote() {
        when(quoteRepository.findRandomQuote()).thenReturn(Optional.of(quote));
        quoteService.getRandomQuote();
        verify(quoteRepository).findRandomQuote();
    }

    @Test
    void testGetRandomQuoteThrowsEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> quoteService.getRandomQuote());
    }

    @Test
    void testGetLastQuotes() {
        List<Quote> list = List.of(quote1, quote2, quote3);
        when(quoteRepository.findLastQuotes(0, null)).thenReturn(list);
        quoteService.getLastQuotes();
        verify(quoteRepository).findLastQuotes(0, null);
    }

    @Test
    void testGetTopQuotes() {
        List<Quote> list = List.of(quote1, quote2, quote3);
        when(quoteRepository.findTopQuotes(0, null)).thenReturn(list);
        quoteService.getTopQuotes();
        verify(quoteRepository).findTopQuotes(0, null);
    }

    @Test
    void testGetFlopQuotes() {
        List<Quote> list = List.of(quote1, quote2, quote3);
        when(quoteRepository.findFlopQuotes(0, null)).thenReturn(list);
        quoteService.getFlopQuotes();
        verify(quoteRepository).findFlopQuotes(0, null);
    }

    @Test
    void deleteQuote() {
        quoteService.deleteQuote(1L);
        verify(quoteRepository).deleteById(1L);
    }

    @Test
    void testVote() {
        when(quoteRepository.findById(1L)).thenReturn(Optional.of(quote));
        quoteService.vote(1L, 1L);
        Vote vote = Vote.builder()
                .user(User.builder().id(1L).build())
                .type(VoteType.LIKE)
                .quote(quote)
                .build();
        verify(voteRepository).save(vote);
        verify(quoteRepository).save(quote);
    }

    @Test
    void testUnVote() {
        when(quoteRepository.findById(1L)).thenReturn(Optional.of(quote));
        quoteService.unVote(1L, 1L);
        Vote vote = Vote.builder()
                .user(User.builder().id(1L).build())
                .type(VoteType.DISLIKE)
                .quote(quote)
                .build();
        verify(voteRepository).save(vote);
        verify(quoteRepository).save(quote);
    }
}