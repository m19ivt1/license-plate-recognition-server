package ru.nntu.lprserver.controller.exception;

public class InternalErrorException extends RuntimeException {

    public InternalErrorException(Throwable cause) {
        super(cause);
    }
}
