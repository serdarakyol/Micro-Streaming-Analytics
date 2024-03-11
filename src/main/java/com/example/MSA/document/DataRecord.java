package com.example.MSA.document;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "data_records")
public class DataRecord {
    @MongoId(value = FieldType.STRING)
    private String id;
    private String version;
    private String device;
    private String path;
    private String trustedBoot;
    private List<DataStream> dataStreams;
}
