package com.example.vacancynotifierservice.repository;

import com.example.vacancynotifierservice.dto.telegram.UserDto;

import java.util.List;

public interface UserRepository {
    List<UserDto> findUsersByQuery(String query);
}
