package com.example.MSA.mapper;

import com.example.MSA.document.DataStream;
import com.example.MSA.dto.DataStreamDTO;

public class DataStreamMapper {
    public static DataStream toEntity(DataStreamDTO dto) {
        return dto != null
                ? DataStream.builder().id(dto.id()).feed(dto.feed())
                        .dataPoint(dto.datapoints().stream().map(DataPointMapper::toEntity).toList()).build()
                : null;
    }
}
