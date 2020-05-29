package com.craftersconquest.exceptions;

public class NotRegisteredException extends ConquestException {

    public NotRegisteredException() {
        super("Not registered.");
    }

    public NotRegisteredException(String message) {
        super(message);
    }
}
