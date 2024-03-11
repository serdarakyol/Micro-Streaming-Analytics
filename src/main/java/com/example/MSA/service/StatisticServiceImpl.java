package com.example.MSA.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.MSA.document.Statistic;
import com.example.MSA.dto.StatisticDTO;
import com.example.MSA.mapper.StatisticMapper;
import com.example.MSA.repository.StatisticRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private StatisticRepository statisticRepository;

    @Override
    public List<StatisticDTO> getStatisticsDTOsWithDates(String startDate, String endDate) {
        Instant instantStartDate = Instant.now();
        Instant instantEndDate = instantStartDate.minus(30, ChronoUnit.MINUTES);
        if (startDate != null) {
            instantStartDate = Instant.parse(startDate);
        }
        if (endDate != null) {
            instantEndDate = Instant.parse(endDate);
        }
        List<Statistic> statistics = statisticRepository.findByCreateAtBetween(instantStartDate, instantEndDate);
        if (statistics.size() == 0) {
            log.warn("There is no statistics on DB");
            return new ArrayList<>();
        }
        log.info("Statistics are returned between {} and {}", instantStartDate.toString(), instantEndDate.toString());
        return statistics.stream().map(StatisticMapper::toDTO).toList();
    }

    @Override
    public List<StatisticDTO> getAllStatistics() {
        return statisticRepository.findAll().stream().map(StatisticMapper::toDTO).toList();
    }

}
