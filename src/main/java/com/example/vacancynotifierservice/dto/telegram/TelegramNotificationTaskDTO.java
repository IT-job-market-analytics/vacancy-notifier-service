package com.example.vacancynotifierservice.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramNotificationTaskDTO {
    @JsonProperty("chat_id")
    private long chatId;
    private String message;
}