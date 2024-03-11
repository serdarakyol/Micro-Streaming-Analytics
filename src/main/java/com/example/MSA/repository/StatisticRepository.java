package com.example.MSA.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.MSA.document.Statistic;
import java.util.List;
import java.time.Instant;


@Repository
public interface StatisticRepository extends MongoRepository<Statistic, String>{
    List<Statistic> findByCreateAtBetween(Instant startDate, Instant endDate);
}
