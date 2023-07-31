package com.example.vacancynotifierservice.service;

import com.example.vacancynotifierservice.dto.hh.Vacancy;
import com.example.vacancynotifierservice.dto.telegram.TelegramNotificationTaskDTO;
import com.example.vacancynotifierservice.dto.telegram.UserDto;
import com.example.vacancynotifierservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private final UserRepository userRepository;
    private final ProducerService producerService;

    @RabbitListener(queues = "${rabbitmq.consumer.new-vacancies-queue}")
    public void consumeVacancy(Vacancy vacancy) {
        log.info("Handling vacancy \"{}\" found by query \"{}\"", vacancy.getName(), vacancy.getQuery());

        List<UserDto> users = userRepository.findUsersByQuery(vacancy.getQuery());
        log.debug("{} user(s) found for query \"{}\"", users.size(), vacancy.getQuery());

        sendMessagesToTelegram(users, composeVacancyMessage(vacancy));
    }

    private void sendMessagesToTelegram(List<UserDto> users, String message){
        for (UserDto user : users) {
            if (user.getTelegramChatId() != null) {
                sendMessage(user.getTelegramChatId(), message);
            } else {
                log.debug("User \"{}\" has no telegram chat id, ignoring", user.getUsername());
            }
        }
    }

    private void sendMessage(long chatId, String message) {
        log.debug("Sending notification to chat {}", chatId);

        TelegramNotificationTaskDTO telegramNotificationTaskDTO = new TelegramNotificationTaskDTO(chatId, message);
        producerService.sendNotification(telegramNotificationTaskDTO);
    }

    private String composeVacancyMessage(Vacancy vacancy) {
        return
                """
                Новая вакансия по вашему запросу "%s"!
                
                %s
                
                %s
                """.formatted(vacancy.getQuery(), vacancy.getName(), vacancy.getAlternateUrl());
    }
}
