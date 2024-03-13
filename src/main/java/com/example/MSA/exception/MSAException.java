package com.example.MSA.exception;

import com.example.MSA.response.ResponseEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MSAException extends RuntimeException {
    private static final long serialVersionUID = -1497952503985975270L;
    private final ResponseEnum exception;
}
