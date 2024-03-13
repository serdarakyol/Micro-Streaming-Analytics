package com.example.MSA.service;

import org.springframework.http.HttpHeaders;

import com.example.MSA.dto.DataRecordDTO;

public interface DataRecordService {
    void addDataRecordToQueue(HttpHeaders headers, DataRecordDTO dataRecordDTO);
}
