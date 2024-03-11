package com.example.MSA.mapper;

import com.example.MSA.document.DataPoint;
import com.example.MSA.dto.DataPointDTO;

public class DataPointMapper {
    public static DataPoint toEntity(DataPointDTO dto) {
        return dto != null ? DataPoint.builder().from(dto.from()).at(dto.at()).value(dto.value()).build() : null;
    }
}
