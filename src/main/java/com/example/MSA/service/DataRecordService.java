package com.example.MSA.service;

import com.example.MSA.dto.DataRecordDTO;

public interface DataRecordService {
    void addDataRecordToQueue(DataRecordDTO dataRecordDTO);
}
