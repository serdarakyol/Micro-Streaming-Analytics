package com.example.MSA.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MSA.dto.DataRecordDTO;
import com.example.MSA.response.Response;
import com.example.MSA.service.DataRecordService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/data_records")
@Tag(name = "Data Records", description = "This module saves new record to MongoDB")
public class DataRecordController {
    private DataRecordService dataRecordService;

    @PostMapping
    public ResponseEntity<Response<Void>> saveDataRecord(@RequestBody DataRecordDTO dataRecordDTO) {
        dataRecordService.saveDataRecord(dataRecordDTO);
        return ResponseEntity.ok().body(Response.<Void>builder().statusCode(HttpStatus.CREATED.value())
                .statusMessage(HttpStatus.CREATED.name()).timestamp(Instant.now().toString()).build());
    }

}
