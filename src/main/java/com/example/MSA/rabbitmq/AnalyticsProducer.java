package com.example.MSA.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.MSA.dto.DataRecordDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AnalyticsProducer {

    private RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(DataRecordDTO message) {
        try {
            log.info("Message arrive to producer: {}", message.toString());
            String json = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(RabbitTopics.ANALYTICS.topic, json);
        } catch (Exception e) {
            log.error("Failed while sending message: {}", e.toString());
        }
    }
}
