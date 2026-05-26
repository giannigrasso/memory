package ch.supsi.memory.backend.business;

import java.util.Properties;

public interface PreferencesDao {

    void write();

    Properties read();

    Properties getDefault();
}
