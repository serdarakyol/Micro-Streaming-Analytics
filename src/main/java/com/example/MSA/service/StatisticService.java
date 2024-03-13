package com.example.MSA.service;

import org.springframework.http.HttpHeaders;

import com.example.MSA.dto.StatisticDTOWithHeaders;

public interface StatisticService {
    StatisticDTOWithHeaders getStatisticsDTOsWithDates(HttpHeaders headers, String startDate, String endDate, Integer offset, Integer limit);
}
