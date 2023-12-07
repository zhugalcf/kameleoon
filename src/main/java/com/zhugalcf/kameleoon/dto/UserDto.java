package com.zhugalcf.kameleoon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    @NotBlank(message = "Username cannot be empty")
    @Size(max = 64, message = "Username must contains less then 64 symbols")
    private String username;
    @Email
    private String email;
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 8, message = "Password must contains more then 8 symbols")
    private String password;
    private LocalDateTime createdAt;
}
