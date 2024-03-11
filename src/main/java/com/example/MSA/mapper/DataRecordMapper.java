package com.example.MSA.mapper;

import com.example.MSA.document.DataRecord;
import com.example.MSA.dto.DataRecordDTO;

public class DataRecordMapper {
    public static DataRecord toEntity(DataRecordDTO dto) {
        return dto != null
                ? DataRecord.builder().version(dto.version()).device(dto.device()).path(dto.path())
                        .trustedBoot(dto.trustedBoot())
                        .dataStreams(dto.datastreams().stream().map(DataStreamMapper::toEntity).toList()).build()
                : null;
    }
}
