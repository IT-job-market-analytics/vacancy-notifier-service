package com.example.vacancynotifierservice.dto.telegram;

public class UserDto {
    String username;
    Integer telegramChatId;

    public UserDto(String username, Integer telegramChatId){
        this.username = username;
        this.telegramChatId = telegramChatId;
    }
}
