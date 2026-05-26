package ch.supsi.memory.frontend.model;

import java.util.Properties;


public interface PreferencesEventHandler extends EventHandler {

    void setIsShowing(boolean showing);

    boolean isShowing();

    void editPreferences(Properties properties);

    int getBatchSize();

    String getLocale();
}
