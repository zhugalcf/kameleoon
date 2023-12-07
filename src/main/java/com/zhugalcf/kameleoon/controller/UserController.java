package com.zhugalcf.kameleoon.controller;

import com.zhugalcf.kameleoon.dto.UserDto;
import com.zhugalcf.kameleoon.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);
        log.info("User with name: {} is created", userDto.getUsername());
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") long userId) {
        return userService.getUser(userId);
    }
}
