package com.example.MSA.service;

import com.example.MSA.dto.StatisticDTOWithHeaders;

public interface StatisticService {
    StatisticDTOWithHeaders getStatisticsDTOsWithDates(String startDate, String endDate, Integer offset, Integer limit);
}
