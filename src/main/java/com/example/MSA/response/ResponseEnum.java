package com.example.MSA.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    SUCCESS(200, "Success", HttpStatus.OK),
    UNAUTHORIZED(401, "The client is not authorized", HttpStatus.UNAUTHORIZED),
    SERVER_ERROR(500, "Server error", HttpStatus.METHOD_NOT_ALLOWED);

    private final Integer statusCode;
    private final String statusMessage;
    private final HttpStatus status;

    ResponseEnum(Integer statusCode, String statusMessage, HttpStatus status) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.status = status;
    }
}
