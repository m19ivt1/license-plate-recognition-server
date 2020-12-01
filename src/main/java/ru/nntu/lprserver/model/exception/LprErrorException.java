package ru.nntu.lprserver.model.exception;

/**
 * Exception used for common license plate recognition exceptions.
 */
public class LprErrorException extends RuntimeException {

    public LprErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
