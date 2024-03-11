package com.example.MSA.dto;

public record DataPointDTO(int from, int at, int value) {
    public static class Builder {
        private int from;
        private int at;
        private int value;

        public Builder from(int from) {
            this.from = from;
            return this;
        }

        public Builder at(int at) {
            this.at = at;
            return this;
        }

        public Builder value(int value) {
            this.value = value;
            return this;
        }

        public DataPointDTO build() {
            return new DataPointDTO(from, at, value);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
