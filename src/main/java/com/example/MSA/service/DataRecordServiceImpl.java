package com.example.MSA.service;

import org.springframework.stereotype.Service;

import com.example.MSA.dto.DataRecordDTO;
import com.example.MSA.mapper.DataRecordMapper;
import com.example.MSA.rabbitmq.AnalyticsProducer;
import com.example.MSA.repository.DataRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DataRecordServiceImpl implements DataRecordService {

    private final DataRecordRepository dataRecordRepository;
    private final AnalyticsProducer analyticsProducer;

    @Override
    public void saveDataRecord(DataRecordDTO dataRecordDTO) {
        dataRecordRepository.save(DataRecordMapper.toEntity(dataRecordDTO));
    }

    @Override
    public void addDataRecordToQueue(DataRecordDTO dataRecordDTO) {
        analyticsProducer.sendMessageToAnalytics(dataRecordDTO);
    }
    
}
