package com.example.MSA.service;

import org.springframework.stereotype.Service;

import com.example.MSA.dto.DataRecordDTO;
import com.example.MSA.mapper.DataRecordMapper;
import com.example.MSA.repository.DataRecordRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DataRecordServiceImpl implements DataRecordService {

    private final DataRecordRepository dataRecordRepository;

    @Override
    public void saveDataRecord(DataRecordDTO dataRecordDTO) {
        dataRecordRepository.save(DataRecordMapper.toEntity(dataRecordDTO));
    }
    
}
