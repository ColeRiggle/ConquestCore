package com.craftersconquest.exceptions;

public class ConquestException extends Exception {

    public ConquestException() {
        super("unknown");
    }

    public ConquestException(String message) {
        super(message);
    }
}
