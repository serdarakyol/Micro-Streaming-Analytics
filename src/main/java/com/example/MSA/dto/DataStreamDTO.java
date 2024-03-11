package com.example.MSA.dto;

import java.util.List;

public record DataStreamDTO(String id, String feed, List<DataPointDTO> datapoints) {
    public static class Builder {
        private String id;
        private String feed;
        private List<DataPointDTO> datapoints;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder feed(String feed) {
            this.feed = feed;
            return this;
        }

        public Builder datapoints(List<DataPointDTO> datapoints) {
            this.datapoints = datapoints;
            return this;
        }

        public DataStreamDTO build() {
            return new DataStreamDTO(id, feed, datapoints);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
