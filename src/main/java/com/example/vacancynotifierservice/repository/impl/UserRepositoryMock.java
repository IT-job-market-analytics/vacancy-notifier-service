package com.example.vacancynotifierservice.repository.impl;

import com.example.vacancynotifierservice.dto.telegram.UserDto;
import com.example.vacancynotifierservice.repository.UserRepository;

import java.util.List;

public class UserRepositoryMock implements UserRepository {
    @Override
    public List<UserDto> findUsersByQuery(String query) {
        return List.of(new UserDto("MockUsername1", 1), new UserDto("MockUsername2", 2));
    }
}
