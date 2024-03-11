package com.example.MSA.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Response<T>(
        @JsonProperty("data") T data,
        @JsonProperty("status_code") Integer statusCode,
        @JsonProperty("status_message") String statusMessage,
        @JsonProperty("timestamp") String timestamp) {

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private T data;
        private Integer statusCode;
        private String statusMessage;
        private String timestamp;

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> statusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public Builder<T> timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Response<T> build() {
            return new Response<>(data, statusCode, statusMessage, timestamp);
        }
    }
}
