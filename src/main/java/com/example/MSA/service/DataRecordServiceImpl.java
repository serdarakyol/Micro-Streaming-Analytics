package com.example.MSA.service;

import org.springframework.stereotype.Service;

import com.example.MSA.dto.DataRecordDTO;
import com.example.MSA.rabbitmq.AnalyticsProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DataRecordServiceImpl implements DataRecordService {

    private final AnalyticsProducer analyticsProducer;

    @Override
    public void addDataRecordToQueue(DataRecordDTO dataRecordDTO) {
        analyticsProducer.sendMessageToAnalytics(dataRecordDTO);
    }
    
}
