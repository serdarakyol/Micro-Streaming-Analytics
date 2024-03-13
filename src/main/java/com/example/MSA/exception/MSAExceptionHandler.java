package com.example.MSA.exception;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.MSA.response.Response;
import com.example.MSA.response.ResponseEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class MSAExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { MSAException.class })
    public ResponseEntity<Response<Void>> handlerCustomException(MSAException msae) {
        Response<Void> response = Response.<Void>builder()
                .statusCode(msae.getException().getStatusCode())
                .statusMessage(msae.getException().getStatusMessage())
                .timestamp(Instant.now().toString()).build();
        StringBuilder sb = new StringBuilder(msae.toString());
        sb.append(msae.getStackTrace()[0].getClassName());
        sb.append(msae.getStackTrace()[0].getMethodName());
        sb.append(String.valueOf(msae.getStackTrace()[0].getLineNumber()));

        return new ResponseEntity<>(response, msae.getException().getStatus());
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Response<Void>> handleException(Exception e) {

        Response<Void> response = Response.<Void>builder()
                .statusCode(ResponseEnum.SERVER_ERROR.getStatusCode())
                .statusMessage(ResponseEnum.SERVER_ERROR.getStatusMessage())
                .timestamp(Instant.now().toString()).build();

        StringBuilder sb = new StringBuilder(e.toString());
        sb.append(e.getStackTrace()[0].getClassName());
        sb.append(e.getStackTrace()[0].getMethodName());
        sb.append(String.valueOf(e.getStackTrace()[0].getLineNumber()));
        log.error(sb.toString());

        return new ResponseEntity<>(response, ResponseEnum.SERVER_ERROR.getStatus());
    }

}
