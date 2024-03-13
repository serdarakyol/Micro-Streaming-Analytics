package com.example.MSA.utils;

import org.springframework.stereotype.Component;

import com.example.MSA.exception.MSAException;
import com.example.MSA.response.ResponseEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BasicSecurity {
    private static final String apiKey = "d05f06b5-61bc-4fc2-9aca-6adde3cacc56";

    public static Boolean checkAuth(String providedKey) {
        if (!apiKey.equals(providedKey)) {
            log.error(ResponseEnum.UNAUTHORIZED.getStatusMessage());
            throw new MSAException(ResponseEnum.UNAUTHORIZED);
        }
        return true;
    }
}
