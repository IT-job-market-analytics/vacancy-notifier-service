package com.example.vacancynotifierservice.repository.impl;

import com.example.vacancynotifierservice.dto.telegram.UserDto;
import com.example.vacancynotifierservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final RestTemplate restTemplate;
    private final String restApiHost;

    public UserRepositoryImpl(RestTemplate restTemplate, @Value("${rest-api.host}") String restApiHost) {
        this.restTemplate = restTemplate;
        this.restApiHost = restApiHost;
    }

    private final String endpoint = "/users/byQuery/";
    @Override
    public List<UserDto> findUsersByQuery(String query) {
        String url = restApiHost.concat(endpoint).concat(query);

        UserDto[] userArray = restTemplate.getForObject(url, UserDto[].class);
        return Arrays.stream(userArray).toList();
    }
}
