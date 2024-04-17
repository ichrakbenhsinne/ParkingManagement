package com.example.demo.Exceptions;

public class DeleteImpossible extends RuntimeException {
    public DeleteImpossible(String message) {
        super(message);
    }
}
