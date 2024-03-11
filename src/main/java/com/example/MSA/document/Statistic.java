package com.example.MSA.document;

import java.time.Instant;

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
@Document(collation = "statistics")
public class Statistic {
    @MongoId(value = FieldType.STRING)
    private String id;
    private double average;
    private double max;
    private double min;
    private double median;
    private double mode;
    private double standardDeviation;
    private double firstQuartile;
    private double secondQuartile;
    private double thirdQuartile;
    private Instant createAt;
}
