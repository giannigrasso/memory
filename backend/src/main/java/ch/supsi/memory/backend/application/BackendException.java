package ch.supsi.memory.backend.application;

public class BackendException extends RuntimeException {

    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackendException(String message) {
        super(message);
    }
}
