package com.example.vacancynotifierservice.service;

import com.example.vacancynotifierservice.dto.hh.Vacancy;
import com.example.vacancynotifierservice.dto.telegram.TelegramNotificationTaskDTO;
import com.example.vacancynotifierservice.dto.telegram.UserDto;
import com.example.vacancynotifierservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
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

    private void sendMessagesToTelegram(List<UserDto> users, String message) {
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
        stringBuilder.append("\n").append(vacancy.getName());

        if (vacancy.getEmployer() != null) {
            stringBuilder.append(" в ").append(vacancy.getEmployer().getName());
        }

        if (vacancy.getArea() != null) {
            stringBuilder.append("\n").append("<b>").append(vacancy.getArea().getName()).append("</b>");
        }

        if (vacancy.getSalary() != null) {
            String salaryFrom = String.valueOf(vacancy.getSalary().getFrom());
            String salaryTo = String.valueOf(vacancy.getSalary().getTo());
            if (!salaryFrom.equals("0") || !salaryTo.equals("0")) {
                stringBuilder.append("\n").append("Оплата: ");
                if (!salaryFrom.equals("0")) {
                    stringBuilder.append("от ").append(salaryFrom).append(" ");
                }
                if (!salaryTo.equals("0")) {
                    stringBuilder.append("до ").append(salaryTo).append(" ");
                }
                stringBuilder.append(vacancy.getSalary().getCurrency());
            }
        }

        if (vacancy.getSnippet() != null) {
            String requirement = vacancy.getSnippet().getRequirement();
            String responsibility = vacancy.getSnippet().getResponsibility();
            if (requirement != null) {
                stringBuilder.append("\n\n").append("Требования: ").append(deleteHtmlTags(requirement));
            }
            if (responsibility != null) {
                stringBuilder.append("\n\n").append("Обязанности: ").append(deleteHtmlTags(responsibility));
            }
        }

        if (vacancy.getExperience() != null) {
            String experience = vacancy.getExperience().getName();
            if (experience != null) {
                stringBuilder.append("\n").append("Опыт: ").append(experience);
            }
        }

        stringBuilder.append("\n");
        stringBuilder.append("\n").append(vacancy.getAlternateUrl());

        return stringBuilder.toString();
    }

    static String deleteHtmlTags(String html) {
        return Jsoup.parse(html).text();
    }
}
