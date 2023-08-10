package com.example.vacancynotifierservice.dto.telegram;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    String username;
    Long telegramChatId;

    public UserDto(String username, Long telegramChatId){
        this.username = username;
        this.telegramChatId = telegramChatId;
    }
}
