package ch.supsi.memory.backend.application;

import java.util.Properties;

public interface PreferencesService {

    String getLocale();

    void setLocale(String locale);

    int getBatchSize();

    void setBatchSize(int batchSize);

    void persist();

    void setPreferences(Properties properties);
}
