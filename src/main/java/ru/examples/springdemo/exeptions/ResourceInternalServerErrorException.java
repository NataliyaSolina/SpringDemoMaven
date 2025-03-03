package ru.examples.springdemo.exeptions;

public class ResourceInternalServerErrorException extends RuntimeException {
    public ResourceInternalServerErrorException(String message) {
        super(message);
    }
}
