package com.zhugalcf.kameleoon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {

    private long id;
    @NotBlank(message = "Content of the quote cannot be empty")
    private String content;
    private long userId;
    private long score;
    private LocalDateTime createdAt;
}
