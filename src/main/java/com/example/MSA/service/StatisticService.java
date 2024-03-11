package com.example.MSA.service;

import java.util.List;

import com.example.MSA.dto.StatisticDTO;

public interface StatisticService {
    List<StatisticDTO> getStatisticsDTOsWithDates(String startDate, String endDate);
    List<StatisticDTO> getAllStatistics();
}
