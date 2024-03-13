package com.example.MSA.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.example.MSA.dto.DataRecordDTO;
import com.example.MSA.exception.MSAException;
import com.example.MSA.rabbitmq.AnalyticsProducer;
import com.example.MSA.response.ResponseEnum;
import com.example.MSA.utils.BasicSecurity;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DataRecordServiceImpl implements DataRecordService {

    private final AnalyticsProducer analyticsProducer;

    @Override
    public void addDataRecordToQueue(HttpHeaders headers, DataRecordDTO dataRecordDTO) {
        if (headers.get("Authorization") == null) {
            log.error(ResponseEnum.UNAUTHORIZED.getStatusMessage());
            throw new MSAException(ResponseEnum.UNAUTHORIZED);
        }

        BasicSecurity.checkAuth(headers.get("Authorization").get(0));
        analyticsProducer.sendMessageToAnalytics(dataRecordDTO);
    }
    
}
