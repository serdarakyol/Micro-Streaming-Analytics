package com.example.MSA.mapper;

import com.example.MSA.document.Statistic;
import com.example.MSA.dto.StatisticDTO;

public class StatisticMapper {
    public static StatisticDTO toDTO(Statistic statistics) {
        return statistics != null
                ? StatisticDTO.builder().average(statistics.getAverage()).max(statistics.getMax())
                        .min(statistics.getMin()).median(statistics.getMedian()).mode(statistics.getMode())
                        .standardDeviation(statistics.getStandardDeviation())
                        .firstQuartile(statistics.getFirstQuartile()).secondQuartile(statistics.getSecondQuartile())
                        .thirdQuartile(statistics.getThirdQuartile()).createAt(statistics.getCreateAt().toString())
                        .build()
                : null;
    }
}
