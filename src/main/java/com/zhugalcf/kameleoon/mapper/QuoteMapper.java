package com.zhugalcf.kameleoon.mapper;

import com.zhugalcf.kameleoon.dto.QuoteDto;
import com.zhugalcf.kameleoon.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.zhugalcf.kameleoon.entity.Quote;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuoteMapper {

    @Mapping(source = "userId", target = "user", qualifiedByName = "toUserEntity")
    @Mapping(target = "score", ignore = true)
    Quote toEntity(QuoteDto quoteDto);

    @Mapping(source = "user", target = "userId", qualifiedByName = "toUserId")
    QuoteDto toDto(Quote quote);

    @Named("toUserEntity")
    default User toUserEntity(long userId) {
        return User.builder().id(userId).build();
    }

    @Named("toUserId")
    default long toUserId(User user) {
        return user.getId();
    }

}
