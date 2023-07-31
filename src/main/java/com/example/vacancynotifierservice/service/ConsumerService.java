package com.example.vacancynotifierservice.service;

import com.example.vacancynotifierservice.dto.hh.Vacancy;
import com.example.vacancynotifierservice.dto.telegram.TelegramNotificationTaskDTO;
import com.example.vacancynotifierservice.dto.telegram.UserDto;
import com.example.vacancynotifierservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;
    private final ProducerService producerService;
    @RabbitListener(queues = "${rabbitmq.consumer.new-vacancies-queue}")
    public void consumeVacancy(Vacancy vacancy) throws JsonProcessingException {
        List<UserDto> users = userRepository.findUsersByQuery(vacancy.getQuery());
        sendMessagesToTelegram(users, getMessage(vacancy));
        log.info("Message consumed: " + objectMapper.writeValueAsString(vacancy));
    }

    private void sendMessagesToTelegram(List<UserDto> users, String message){
        for (UserDto user:
             users) {
            try {
                sendMessage(user.getTelegramChatId(), message);
            }catch (NullPointerException ex){
                log.error(ex.getMessage());
            }

        }
    }
    private void sendMessage(long chatId, String message){
        TelegramNotificationTaskDTO telegramNotificationTaskDTO = new TelegramNotificationTaskDTO(chatId, message);
        producerService.sendNotification(telegramNotificationTaskDTO);
        log.info("Notification task sent");
    }
    private String getMessage(Vacancy vacancy){
        return "test";
    }
}
