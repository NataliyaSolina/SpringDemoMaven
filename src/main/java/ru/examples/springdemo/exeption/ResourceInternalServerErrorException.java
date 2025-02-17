package ru.examples.springdemo.exeption;

public class ResourceInternalServerErrorException extends RuntimeException {
    public ResourceInternalServerErrorException(String message) {
        super(message);
    }
}
