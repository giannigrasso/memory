package ch.supsi.memory.backend.business;

import java.util.Properties;

public class BadUserPreferencesException extends BusinessException {

    private final Properties preferences;

    public BadUserPreferencesException(String message, Properties preferences, Throwable cause) {
        super(message, cause);
        this.preferences = preferences;
    }

    public BadUserPreferencesException(String message, Properties preferences) {
        super(message);
        this.preferences = preferences;
    }
}
