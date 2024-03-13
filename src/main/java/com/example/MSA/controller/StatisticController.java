package com.example.MSA.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MSA.dto.StatisticDTO;
import com.example.MSA.dto.StatisticDTOWithHeaders;
import com.example.MSA.response.Response;
import com.example.MSA.service.StatisticService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
@Tag(name = "Statistics", description = "This module used for obtain statistics")
public class StatisticController {

    private StatisticService statisticService;

    @GetMapping
    public ResponseEntity<Response<List<StatisticDTO>>> getStatisticsBetweenDates(@RequestHeader HttpHeaders headers,
            @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        StatisticDTOWithHeaders statisticDTOWithHeaders = statisticService.getStatisticsDTOsWithDates(headers,
                startDate,endDate, offset, limit);
        Response<List<StatisticDTO>> responseBody = Response.<List<StatisticDTO>>builder()
                .data(statisticDTOWithHeaders.statisticDTOs()).statusCode(HttpStatus.OK.value())
                .statusMessage(HttpStatus.OK.name()).timestamp(Instant.now().toString()).build();
        return ResponseEntity.ok().headers(statisticDTOWithHeaders.headers()).body(responseBody);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "API is working";
    }

}
