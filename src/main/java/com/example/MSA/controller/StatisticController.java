package com.example.MSA.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MSA.dto.StatisticDTO;
import com.example.MSA.response.Response;
import com.example.MSA.service.StatisticService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
@RequestMapping("/statistics")
@Tag(name = "Statistics", description = "This module used for obtain statistics")
public class StatisticController {

    private StatisticService statisticService;

    @GetMapping
    public ResponseEntity<Response<List<StatisticDTO>>> getStatisticsBetweenDates(
            @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok().body(Response.<List<StatisticDTO>>builder()
                .data(statisticService.getStatisticsDTOsWithDates(startDate, endDate)).statusCode(HttpStatus.OK.value())
                .statusMessage(HttpStatus.OK.name()).timestamp(Instant.now().toString()).build());
    }

    @GetMapping("all")
    public ResponseEntity<Response<List<StatisticDTO>>> getAllStatistics() {
        return ResponseEntity.ok().body(Response.<List<StatisticDTO>>builder()
                .data(statisticService.getAllStatistics()).statusCode(HttpStatus.OK.value())
                .statusMessage(HttpStatus.OK.name()).timestamp(Instant.now().toString()).build());
    }
    

}
