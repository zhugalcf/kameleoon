package com.zhugalcf.kameleoon.service;

import com.zhugalcf.kameleoon.dto.QuoteDto;
import com.zhugalcf.kameleoon.entity.Quote;
import com.zhugalcf.kameleoon.entity.User;
import com.zhugalcf.kameleoon.entity.Vote;
import com.zhugalcf.kameleoon.entity.VoteType;
import com.zhugalcf.kameleoon.exception.EntityNotFoundException;
import com.zhugalcf.kameleoon.exception.VoteAlreadyExistException;
import com.zhugalcf.kameleoon.mapper.QuoteMapper;
import com.zhugalcf.kameleoon.repository.QuoteRepository;
import com.zhugalcf.kameleoon.repository.VoteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final VoteRepository voteRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Value("${parameters.batch-size}")
    private int batchSize;

    @Transactional
    public void createQuote(QuoteDto quoteDto) {
        Quote entity = quoteMapper.toEntity(quoteDto);
        entity.setScore(0L);
        quoteRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public QuoteDto getRandomQuote() {
        Quote quote = quoteRepository.findRandomQuote()
                .orElseThrow(() -> new EntityNotFoundException("Quote list is empty"));
        return quoteMapper.toDto(quote);
    }

    @Transactional(readOnly = true)
    public List<QuoteDto> getLastQuotes() {
        return quoteRepository.findLastQuotes(batchSize, entityManager).stream()
                .map(quoteMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuoteDto> getTopQuotes() {
        return quoteRepository.findTopQuotes(batchSize, entityManager).stream()
                .map(quoteMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuoteDto> getFlopQuotes() {
        return quoteRepository.findFlopQuotes(batchSize, entityManager).stream()
                .map(quoteMapper::toDto)
                .toList();
    }

    @Transactional
    public void updateQuote(QuoteDto quoteDto) {
        Quote quote = quoteRepository.findById(quoteDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("Quote with id: %d doesn't exist", quoteDto.getId())));
        quote.setContent(quoteDto.getContent());
    }

    @Transactional
    public void deleteQuote(long quoteId) {
        quoteRepository.deleteById(quoteId);
        log.info("Quote with id: {} successful deleted", quoteId);
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 3, backoff = @Backoff(delay = 300))
    public void vote(long quoteId, long userId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("Quote with id: %d doesn't exist", quoteId)));
        try {
            voteRepository.save(Vote.builder()
                    .user(User.builder().id(userId).build())
                    .type(VoteType.LIKE)
                    .quote(quote)
                    .build());
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new VoteAlreadyExistException("Vote already exist");
        }
        quote.voteIncrement();
        quoteRepository.save(quote);
    }

    /*
    ADD "ALTER TABLE votes ADD CONSTRAINT unique_q UNIQUE (user_id, quote_id);"
    At H2 console directly, because of from Spring 2.1 version doesn't support index insert to H2 database
     */
    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 3, backoff = @Backoff(delay = 300))
    public void unVote(long quoteId, long userId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new EntityNotFoundException(String
                        .format("Quote with id: %d doesn't exist", quoteId)));
        try {
            voteRepository.save(Vote.builder()
                    .user(User.builder().id(userId).build())
                    .type(VoteType.DISLIKE)
                    .quote(quote)
                    .build());
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new VoteAlreadyExistException("Vote already exist");
        }
        quote.voteDecrement();
        quoteRepository.save(quote);
    }
}
