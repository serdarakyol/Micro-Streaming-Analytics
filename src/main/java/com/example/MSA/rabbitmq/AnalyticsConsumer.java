package com.example.MSA.rabbitmq;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.MSA.document.Statistic;
import com.example.MSA.dto.DataRecordDTO;
import com.example.MSA.repository.StatisticRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AnalyticsConsumer {

    private final Integer poolingTime;
    private final ObjectMapper objectMapper;
    private final StatisticRepository statisticRepository;
    private List<DataRecordDTO> dataRecordBatch;
    private Timer timer;

    public AnalyticsConsumer(@Value("${rabbitmq.pooling.time}") String poolingTime,
            StatisticRepository statisticRepository) {
        this.poolingTime = Integer.parseInt(poolingTime);
        this.objectMapper = new ObjectMapper();
        this.statisticRepository = statisticRepository;
        this.dataRecordBatch = new ArrayList<>();
        this.timer = new Timer();
        this.poolStatistics();
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void listen(String stringDataRecordDTO) {
        try {
            DataRecordDTO dataRecordDTO = objectMapper.readValue(stringDataRecordDTO.concat("as"),
                    DataRecordDTO.class);
            dataRecordBatch.add(dataRecordDTO);
        } catch (Exception e) {
            log.error("ERROR converting the object on {}: {}", this.getClass(), e.toString());
        }
    }

    public void poolStatistics() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                calculateStatistics();
                dataRecordBatch.clear();
                log.info("Resetting timer --> ".concat(Instant.now().toString()));
            }
        };
        timer.scheduleAtFixedRate(task, 0, poolingTime);
    }

    private void calculateStatistics() {
        if (dataRecordBatch.size() == 0) {
            return;
        }
        double[] values = dataRecordBatch.stream().flatMap(dr -> dr.datastreams().stream())
                .flatMap(ds -> ds.datapoints().stream()).mapToDouble(dp -> dp.value()).toArray();
        Arrays.parallelSort(values);
        Statistic statistics = Statistic.builder().average(calculateAverage(values)).max(findMax(values))
                .min(findMin(values)).median(calculateMedian(values)).mode(calculateMode(values))
                .standardDeviation(calculateStandardDeviation(values)).firstQuartile(calculateFirstQuartile(values))
                .secondQuartile(calculateSecondQuartile(values)).thirdQuartile(calculateThirdQuartile(values))
                .createAt(Instant.now()).build();

        statisticRepository.save(statistics);
    }

    private double calculateAverage(double[] values) {
        return Arrays.stream(values).average().orElse(Double.NaN);
    }

    private double findMax(double[] values) {
        return Arrays.stream(values).max().orElse(Double.NaN);
    }

    private double findMin(double[] values) {
        return Arrays.stream(values).min().orElse(Double.NaN);
    }

    private double calculateMedian(double[] values) {
        int middle = values.length / 2;
        return values.length % 2 == 0 ? (values[middle - 1] + values[middle]) / 2.0 : values[middle];
    }

    private double calculateMode(double[] values) {
        Map<Double, Long> frequencyMap = Arrays.stream(values)
                .boxed()
                .collect(Collectors.groupingBy(d -> d, Collectors.counting()));
        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(Double.NaN);
    }

    private double calculateStandardDeviation(double[] values) {
        double mean = this.calculateAverage(values);
        double variance = Arrays.stream(values)
                .map(x -> Math.pow(x - mean, 2))
                .average()
                .orElse(Double.NaN);
        return Math.sqrt(variance);
    }

    private double calculateFirstQuartile(double[] values) {
        double median = this.calculateMedian(values);
        double[] firstQuartileValues = Arrays.stream(values).filter(v -> v <= median).toArray();
        return this.calculateMedian(firstQuartileValues);
    }

    private double calculateSecondQuartile(double[] values) {
        return this.calculateMedian(values);
    }

    private double calculateThirdQuartile(double[] values) {
        double median = this.calculateMedian(values);
        double[] thirdQuartileValues = Arrays.stream(values).filter(v -> v >= median).toArray();
        return this.calculateMedian(thirdQuartileValues);
    }
}
