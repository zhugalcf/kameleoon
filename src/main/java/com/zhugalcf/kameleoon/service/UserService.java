package com.zhugalcf.kameleoon.service;

import com.zhugalcf.kameleoon.dto.UserDto;
import com.zhugalcf.kameleoon.entity.User;
import com.zhugalcf.kameleoon.exception.EntityNotFoundException;
import com.zhugalcf.kameleoon.mapper.UserMapper;
import com.zhugalcf.kameleoon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    public UserDto getUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(String.format("User with id %d doesn't exist", userId)));
        return userMapper.toDto(user);
    }
}
