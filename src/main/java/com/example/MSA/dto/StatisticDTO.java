package com.example.MSA.dto;

public record StatisticDTO(
        double average, double max, double min, double median, double mode, double standardDeviation,
        double firstQuartile, double secondQuartile, double thirdQuartile, String createAt) {
    public static class Builder {
        private double average;
        private double max;
        private double min;
        private double median;
        private double mode;
        private double standardDeviation;
        private double firstQuartile;
        private double secondQuartile;
        private double thirdQuartile;
        private String createAt;

        public Builder average(double average) {
            this.average = average;
            return this;
        }

        public Builder max(double max) {
            this.max = max;
            return this;
        }

        public Builder min(double min) {
            this.min = min;
            return this;
        }

        public Builder median(double median) {
            this.median = median;
            return this;
        }

        public Builder mode(double mode) {
            this.mode = mode;
            return this;
        }

        public Builder standardDeviation(double standardDeviation) {
            this.standardDeviation = standardDeviation;
            return this;
        }

        public Builder firstQuartile(double firstQuartile) {
            this.firstQuartile = firstQuartile;
            return this;
        }

        public Builder secondQuartile(double secondQuartile) {
            this.secondQuartile = secondQuartile;
            return this;
        }

        public Builder thirdQuartile(double thirdQuartile) {
            this.thirdQuartile = thirdQuartile;
            return this;
        }

        public Builder createAt(String createAt) {
            this.createAt = createAt;
            return this;
        }

        public StatisticDTO build() {
            return new StatisticDTO(average, max, min, median, mode, standardDeviation, firstQuartile, secondQuartile,
                    thirdQuartile, createAt);
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}
