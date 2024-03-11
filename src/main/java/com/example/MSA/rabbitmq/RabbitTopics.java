package com.example.MSA.rabbitmq;

public enum RabbitTopics {
    ANALYTICS("analytics");

    public final String topic;

    RabbitTopics(String topic) {
        this.topic = topic;
    }
}
