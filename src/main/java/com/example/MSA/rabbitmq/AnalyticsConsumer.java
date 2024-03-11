package com.example.MSA.rabbitmq;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.MSA.dto.DataRecordDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AnalyticsConsumer {

    private final Integer poolingTime;
    private final ObjectMapper objectMapper;
    private List<DataRecordDTO> dataRecordBatch;
    private Timer timer;

    public AnalyticsConsumer(@Value("${rabbitmq.pooling.time}") String poolingTime) {
        this.poolingTime = Integer.parseInt(poolingTime);
        this.objectMapper = new ObjectMapper();
        this.dataRecordBatch = new ArrayList<>();
        this.timer = new Timer();
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void listen(String stringDataRecordDTO) {
        try {
            DataRecordDTO dataRecordDTO = objectMapper.readValue(stringDataRecordDTO.concat("as"),
                    DataRecordDTO.class);
            dataRecordBatch.add(dataRecordDTO);
            this.poolStatistics();
        } catch (Exception e) {
            log.error("ERROR converting the object on {}: {}", this.getClass(), e.toString());
        }

    }

    public void poolStatistics() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                calculateStatistics();
                //dataRecordBatch.clear();
                System.out.println(poolingTime.toString().concat(" seconds have passed. Resetting timer..."));
            }
        };
        timer.scheduleAtFixedRate(task, 0, poolingTime);
    }

    private void calculateStatistics() {
        // TODO: calcilate statistics
    }

}
