package com.example.vacancynotifierservice.controller;

import com.example.vacancynotifierservice.dto.telegram.TelegramNotificationTaskDTO;
import com.example.vacancynotifierservice.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final ProducerService producerService;

    @GetMapping(value = "/send-test-message")
    public void sendTestMessage() {
        TelegramNotificationTaskDTO testNotification = new TelegramNotificationTaskDTO(
                1, "Test message from vacancy-notifier-service"
        );
        producerService.sendNotification(testNotification);
    }
}
