package com.example.vacancynotifierservice.service;

import com.example.vacancynotifierservice.dto.hh.Vacancy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {
    private ObjectMapper objectMapper = new ObjectMapper();
    @RabbitListener(queues = "${rabbitmq.consumer.new-vacancies-queue}")
    public void consumeVacancy(Vacancy vacancy) throws JsonProcessingException {
        log.info("Message consumed: " + objectMapper.writeValueAsString(vacancy));
    }
}
