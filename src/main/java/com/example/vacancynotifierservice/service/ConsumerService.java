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
        StringBuilder stringBuilder = new StringBuilder("Новая вакансия по вашему запросу: ");
        stringBuilder.append("<b>#").append(vacancy.getQuery()).append("</b>");
        stringBuilder.append("\n");
        stringBuilder.append("\n").append("<u>").append(vacancy.getName()).append("</u>");
        stringBuilder.append("\n").append("<b>").append(vacancy.getArea().getName()).append("</b>");

        String salaryFrom = String.valueOf(vacancy.getSalary().getFrom());
        String salaryTo = String.valueOf(vacancy.getSalary().getTo());
        if (!salaryFrom.equals("0") || !salaryTo.equals("0")) {
            stringBuilder.append("\n").append("Оплата: ");
            if (!salaryFrom.equals("0")) {
                stringBuilder.append("от ").append(salaryFrom).append(" ");
            }
            if (!salaryTo.equals("0")) {
                stringBuilder.append("до ").append(salaryTo);
            }
            stringBuilder.append(vacancy.getSalary().getCurrency());
        }

        stringBuilder.append("\n");
        stringBuilder.append("\n").append("<u>").append(vacancy.getEmployer().getName()).append("</u>");

        String requirement = vacancy.getSnippet().getRequirement();
        String responsibility = vacancy.getSnippet().getResponsibility();
        String experience = vacancy.getExperience().getName();
        log.info("snippets -> " + requirement + " -> "+responsibility + " -> " + experience);
        if (requirement != null) {
            stringBuilder.append("\n").append("<u>Требования:</u> ").append(requirement);
        }
        if (responsibility != null) {
            stringBuilder.append("\n").append("<u>Обязанности:</u> ").append(responsibility);
        }
        if (experience != null) {
            stringBuilder.append("\n").append("<u>Опыт:</u> ").append(experience);
        }

        stringBuilder.append("\n");
        stringBuilder.append("\n").append("Ссылка на вакансию -> ").append(vacancy.getAlternateUrl());



        return stringBuilder.toString();
//                """
//                Новая вакансия по вашему запросу "%s"!
//
//                %s
//
//                %s
//                """.formatted(vacancy.getQuery(), vacancy.getName(), vacancy.getAlternateUrl());
    }
}
