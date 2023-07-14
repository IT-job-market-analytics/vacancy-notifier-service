package com.example.vacancynotifierservice.service;

import com.example.vacancynotifierservice.dto.telegram.TelegramNotificationTaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {
    @Value("${rabbitmq.producer.telegram-notifications-queue}")
    private String telegramNotificationsQueue;
    private  final RabbitTemplate rabbitTemplate;

    public void sendNotification(TelegramNotificationTaskDTO telegramNotificationTaskDTO){
        rabbitTemplate.convertAndSend(telegramNotificationsQueue, telegramNotificationTaskDTO);
    }
}
