package com.zhugalcf.kameleoon.mapper;

import com.zhugalcf.kameleoon.dto.VoteDto;
import com.zhugalcf.kameleoon.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
    VoteDto toDto(Vote vote);
}
