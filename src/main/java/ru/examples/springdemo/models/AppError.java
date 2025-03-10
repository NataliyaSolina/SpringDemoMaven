package ru.examples.springdemo.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AppError {
    private final int statusCode;
    private final String status;
    private final String message;
}
