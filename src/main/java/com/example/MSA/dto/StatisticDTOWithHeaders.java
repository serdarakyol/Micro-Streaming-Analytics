package com.example.MSA.dto;

import java.util.List;

import org.springframework.http.HttpHeaders;

public record StatisticDTOWithHeaders(List<StatisticDTO> statisticDTOs, HttpHeaders headers) {
    public static class Builder {
        private List<StatisticDTO> statisticDTOs;
        private HttpHeaders headers;

        public Builder statisticDTOs(List<StatisticDTO> statisticDTOs) {
            this.statisticDTOs = statisticDTOs;
            return this;
        }

        public Builder headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        public StatisticDTOWithHeaders build() {
            return new StatisticDTOWithHeaders(statisticDTOs, headers);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
