package com.example.MSA.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.example.MSA.document.Statistic;
import com.example.MSA.dto.StatisticDTOWithHeaders;
import com.example.MSA.mapper.StatisticMapper;
import com.example.MSA.properties.PropertiesMSA;
import com.example.MSA.repository.StatisticRepository;
import com.example.MSA.utils.Filter;
import com.example.MSA.utils.HeaderResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private StatisticRepository statisticRepository;
    private PropertiesMSA propertiesMSA;

    @Override
    public StatisticDTOWithHeaders getStatisticsDTOsWithDates(String startDate, String endDate, Integer offset,
            Integer limit) {
        Filter filter = new Filter(startDate, endDate, offset, limit);
        Integer totalStatistics = statisticRepository.countByCreateAtBetween(filter.getStartDate(),
                filter.getEndDate());
        if (totalStatistics == 0) {
            log.warn("There is no statistics in DB");
            return StatisticDTOWithHeaders.builder().statisticDTOs(new ArrayList<>()).build();
        }
        List<Statistic> statistics = statisticRepository.findByCreateAtBetween(filter.getStartDate(),
                filter.getEndDate(), filter.getPageable());
        HeaderResponse responseHeader = new HeaderResponse(propertiesMSA.getHost());
        HttpHeaders headersResponse = responseHeader.getHeadersResponse(filter, totalStatistics, "statistics");
        log.info("Statistics are returned between {} and {}", filter.getStartDate().toString(),
                filter.getEndDate().toString());
        StatisticDTOWithHeaders a = StatisticDTOWithHeaders.builder()
                .statisticDTOs(statistics.stream().map(StatisticMapper::toDTO).toList()).headers(headersResponse)
                .build();
        return a;
    }

}
