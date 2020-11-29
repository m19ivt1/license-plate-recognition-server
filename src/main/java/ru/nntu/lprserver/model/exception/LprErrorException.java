package ru.nntu.lprserver.model.exception;

public class LprErrorException extends RuntimeException {

    public LprErrorException(Throwable cause) {
        super(cause);
    }

    public LprErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
