package ch.supsi.memory.frontend.controller;

import java.util.Properties;

public interface PreferencesEventController extends EventController {

    void showPreferences();

    void editPreferences(Properties properties);

    String getLocale();

    int getBatchSize();
}
