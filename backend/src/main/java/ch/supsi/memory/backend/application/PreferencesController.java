package ch.supsi.memory.backend.application;

import ch.supsi.memory.backend.business.PreferencesPropertiesService;
import ch.supsi.memory.backend.business.validate.UserPreferencesRule;

import java.util.Properties;

public class PreferencesController {

    private static PreferencesController myself;

    private final PreferencesService prefsService;

    protected PreferencesController() {
        this.prefsService = PreferencesPropertiesService.getInstance();
    }

    public static PreferencesController getInstance() {
        if (myself == null) {
            myself = new PreferencesController();
        }

        return myself;
    }

    public void persist() {
        try {
            this.prefsService.persist();
        } catch (Exception e) {
            throw new BackendException("failed to persist user preferences", e);
        }
    }

    public String getLocale() {
        return this.prefsService.getLocale();
    }

    public void setLocale(String locale) {
        if (locale == null || locale.isBlank()) {
            throw new BackendException("bad locale value");
        }

        this.prefsService.setLocale(locale);
    }

    public int getBatchSize() {
        return this.prefsService.getBatchSize();
    }

    public void setBatchSize(int batchSize) {
        if (!UserPreferencesRule.BatchSizeRule.isValid(batchSize)) {
            throw new BackendException(UserPreferencesRule.BatchSizeRule.ERROR);
        }

        this.prefsService.setBatchSize(batchSize);
    }

    public void editPreferences(Properties properties) {
        if (properties != null) {
            prefsService.setPreferences(properties);
        }
    }
}
