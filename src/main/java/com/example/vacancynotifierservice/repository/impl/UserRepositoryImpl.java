package com.example.vacancynotifierservice.repository.impl;

import com.example.vacancynotifierservice.dto.telegram.UserDto;
import com.example.vacancynotifierservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RequiredArgsConstructor
@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final RestTemplate restTemplate;
    private final String URI = "http://localhost:8080/users/byQuery/";
    @Override
    public List<UserDto> findUsersByQuery(String query) {
        String fullURI = URI.concat(query);

        UserDto[] userArray = restTemplate.getForObject(fullURI,UserDto[].class);
        return Arrays.stream(userArray).toList();
    }
}
