package ch.supsi.memory.backend.business;

import ch.supsi.memory.backend.application.PreferencesService;
import ch.supsi.memory.backend.business.validate.UserPreferencesRule;
import ch.supsi.memory.backend.dataaccess.DataAccessException;
import ch.supsi.memory.backend.dataaccess.PreferencesPropertiesDao;

import java.util.Properties;

import static ch.supsi.memory.backend.business.validate.UserPreferencesRule.KEY_BATCH_SIZE;
import static ch.supsi.memory.backend.business.validate.UserPreferencesRule.KEY_LOCALE;

public class PreferencesPropertiesService implements PreferencesService {

    private static PreferencesPropertiesService model;

    private static final PreferencesDao preferencesDao = PreferencesPropertiesDao.getInstance();

    private final Properties userPreferences;

    protected PreferencesPropertiesService(Properties userPreferences) {
        this.userPreferences = userPreferences;
    }

    public static PreferencesService getInstance() {
        if (model == null) {
            Properties userPreferences;
            try {
                userPreferences = preferencesDao.read();
            } catch (DataAccessException e) {
                throw new BadUserPreferencesException("failed to read user preferences", new Properties(), e);
            }
            if (!UserPreferencesRule.isValid(userPreferences)) {
                throw new BadUserPreferencesException(UserPreferencesRule.ERROR, userPreferences);
            }
            model = new PreferencesPropertiesService(userPreferences);
        }

        return model;
    }

    private String getPreference(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }

        if (this.userPreferences == null) {
            return null;
        }

        return this.userPreferences.getProperty(key);
    }

    private void setPreference(String key, String value) {
        if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
            return;
        }

        if (this.userPreferences == null) {
            return;
        }

        this.userPreferences.setProperty(key, value);
    }

    @Override
    public String getLocale() {
        return this.getPreference(KEY_LOCALE);
    }

    @Override
    public void setLocale(String locale) {
        this.setPreference(KEY_LOCALE, locale);
    }

    @Override
    public int getBatchSize() {
        String value = this.getPreference(KEY_BATCH_SIZE);
        return Integer.parseInt(value);
    }

    @Override
    public void setBatchSize(int batchSize) {
        this.setPreference(KEY_BATCH_SIZE, "" + batchSize);
    }

    @Override
    public void persist() {
        if (this.userPreferences == null) {
            return;
        }

        preferencesDao.write();
    }

    @Override
    public void setPreferences(Properties properties) {
        for (Object e : properties.keySet()) {
            String key = (String) e;

            this.setPreference(key, properties.getProperty(key));
        }
    }
}
