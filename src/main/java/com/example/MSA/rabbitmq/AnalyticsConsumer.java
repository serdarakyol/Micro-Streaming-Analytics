package com.example.MSA.rabbitmq;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private ExecutorService executorService;

    public AnalyticsConsumer(@Value("${rabbitmq.pooling.time}") String poolingTime,
            StatisticRepository statisticRepository) {
        this.poolingTime = Integer.parseInt(poolingTime);
        this.objectMapper = new ObjectMapper();
        this.statisticRepository = statisticRepository;
        this.dataRecordBatch = new ArrayList<>();
        this.timer = new Timer();
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
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
                executorService.shutdown();
                System.out.println(poolingTime.toString().concat(" seconds have passed. Resetting timer..."));
            }
        };
        timer.scheduleAtFixedRate(task, 0, poolingTime);
    }

    private void calculateStatistics() {
        double[] values = dataRecordBatch.stream().flatMap(dr -> dr.datastreams().stream())
                .flatMap(ds -> ds.datapoints().stream()).mapToDouble(dp -> dp.value()).toArray();

        if (values.length == 0) {
            return;
        }
        Statistic statistics = Statistic.builder().average(calculateAverage(values)).max(findMax(values))
                .min(findMin(values)).median(calculateMedian(values)).mode(calculateMode(values))
                .standardDeviation(calculateStandardDeviation(values)).firstQuartile(calculateQuartile(1, values))
                .secondQuartile(calculateQuartile(2, values)).thirdQuartile(calculateQuartile(3, values))
                .createAt(Instant.now()).build();

        statisticRepository.save(statistics);
        log.info("average: {}", statistics.getAverage());
        log.info("findMax: {}", statistics.getMax());
        log.info("findMin: {}", statistics.getMin());
        log.info("calculateMedian: {}", statistics.getMedian());
        log.info("calculateMode: {}", statistics.getMode());
        log.info("calculateStandardDeviation: {}", statistics.getStandardDeviation());
        log.info("calculateQuartile1: {}", statistics.getFirstQuartile());
        log.info("calculateQuartile2: {}", statistics.getSecondQuartile());
        log.info("calculateQuartile3: {}", statistics.getThirdQuartile());
        log.info("Create AT: {}", statistics.getCreateAt().toString());
    }

    private double calculateAverage(double[] values) {
        try {
            return executorService.submit(() -> Arrays.stream(values).average().orElse(Double.NaN)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Concurrency error: {}", e.toString());
            return Double.NaN;
        } //Arrays.stream(values).parallel().average().orElse(Double.NaN);
    }

    private double findMax(double[] values) {
        return Arrays.stream(values).parallel().max().orElse(Double.NaN);
    }

    private double findMin(double[] values) {
        return Arrays.stream(values).parallel().min().orElse(Double.NaN);
    }

    private double calculateMedian(double[] values) {
        Arrays.parallelSort(values);
        int middle = values.length / 2;
        if (values.length % 2 == 0) {
            return (values[middle - 1] + values[middle]) / 2.0;
        } else {
            return values[middle];
        }
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
        double mean = Arrays.stream(values).parallel().average().orElse(Double.NaN);
        double variance = Arrays.stream(values)
                .parallel()
                .map(x -> Math.pow(x - mean, 2))
                .average()
                .orElse(Double.NaN);
        return Math.sqrt(variance);
    }

    private double calculateQuartile(int quartile, double[] values) {
        int index = (int) ((quartile / 4.0) * (values.length + 1));
        return values[index];
    }
}
