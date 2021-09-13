package com.desafio.builders.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super(String.format("Id %d not found", id));
    }
    public NotFoundException(String msg) {
        super(msg);
    }
}

