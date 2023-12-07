package com.zhugalcf.kameleoon.repository;

import com.zhugalcf.kameleoon.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndQuoteId(long userId, long quoteId);

    @Query("SELECT v FROM Vote v WHERE v.quote.id = :quoteId ORDER BY v.createdAt")
    List<Vote> findAllQuoteVotes(@Param("quoteId") long quoteId);
}
