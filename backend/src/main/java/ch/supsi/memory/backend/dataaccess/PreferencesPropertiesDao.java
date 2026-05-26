package ch.supsi.memory.backend.dataaccess;

import ch.supsi.memory.backend.business.PreferencesDao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PreferencesPropertiesDao implements PreferencesDao {

    private static final String defaultPreferencesPath = "/default-user-preferences.properties";

    private static final String userHomeDirectory = System.getProperty("user.home");

    private static final String preferencesDirectory = ".g01memory";

    private static final String preferencesFile = "preferences.properties";

    private static PreferencesPropertiesDao myself;

    private static Properties userPreferences;

    protected PreferencesPropertiesDao() {
    }

    public static PreferencesDao getInstance() {
        if (myself == null) {
            myself = new PreferencesPropertiesDao();
        }

        return myself;
    }

    @Override
    public void write() {
        if (userPreferences == null) {
            return;
        }

        if (!userPreferencesFileExists()) {
            this.createUserPreferencesFile(userPreferences);
            return;
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(this.getUserPreferencesFilePath()));
            userPreferences.store(outputStream, null);
        } catch (IOException e) {
            throw new DataAccessException("failed to write user preferences", e);
        }
    }

    @Override
    public Properties read() {
        if (userPreferences != null) {
            return userPreferences;
        }

        if (userPreferencesFileExists()) {
            userPreferences = this.loadPreferences(this.getUserPreferencesFilePath());
        } else {
            userPreferences = this.loadDefaultPreferences();
            this.createUserPreferencesFile(userPreferences);
        }

        return userPreferences;
    }

    @Override
    public Properties getDefault() {
        return this.loadDefaultPreferences();
    }

    private boolean userPreferencesDirectoryExists() {
        return Files.exists(this.getUserPreferencesDirectoryPath());
    }

    private Path getUserPreferencesDirectoryPath() {
        return Path.of(userHomeDirectory, preferencesDirectory);
    }

    private Path createUserPreferencesDirectory() {
        try {
            return Files.createDirectories(this.getUserPreferencesDirectoryPath());
        } catch (IOException e) {
            throw new DataAccessException("failed to create user preferences directory", e);
        }
    }

    private boolean userPreferencesFileExists() {
        return Files.exists(this.getUserPreferencesFilePath());
    }

    private Path getUserPreferencesFilePath() {
        return Path.of(userHomeDirectory, preferencesDirectory, preferencesFile);
    }

    private boolean createUserPreferencesFile(Properties defaultPreferences) {
        if (defaultPreferences == null) {
            return false;
        }

        if (!userPreferencesDirectoryExists()) {
            // user preferences directory does not exist
            // create it
            this.createUserPreferencesDirectory();
        }

        if (!userPreferencesFileExists()) {
            // user preferences file does not exist
            // create it
            try {
                // create user preferences file (with default preferences)
                FileOutputStream outputStream = new FileOutputStream(String.valueOf(this.getUserPreferencesFilePath()));
                defaultPreferences.store(outputStream, null);
                return true;

            } catch (IOException e) {
                throw new DataAccessException("failed to create user preferences file", e);
            }
        }

        return true;
    }

    private Properties loadPreferences(Path path) {
        Properties preferences = new Properties();
        try {
            preferences.load(new FileInputStream(String.valueOf(path)));
        } catch (IOException e) {
            throw new DataAccessException("failed to load user preferences", e);
        }

        return preferences;
    }

    private Properties loadDefaultPreferences() {
        Properties defaultPreferences = new Properties();
        try {
            InputStream defaultPreferencesStream = this.getClass().getResourceAsStream(defaultPreferencesPath);
            defaultPreferences.load(defaultPreferencesStream);
        } catch (IOException e) {
            throw new DataAccessException("failed to load default user preferences. missing resources/default-user-preferences.properties ?", e);
        }

        return defaultPreferences;
    }
}
