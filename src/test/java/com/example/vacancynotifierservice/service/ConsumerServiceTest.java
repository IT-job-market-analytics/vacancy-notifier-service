package com.example.vacancynotifierservice.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConsumerServiceTest {

    @Test
    public void deleteHtmlTags_should_without_tags() {

        String rawString = "<highlighttext>Жил</highlighttext> был у <u>бабушки</u> <i>серенький</i> <b>козлик</b>";
        String resultString = "Жил был у бабушки серенький козлик";

        assertThat(ConsumerService.deleteHtmlTags(rawString)).isEqualTo(resultString);
    }
}