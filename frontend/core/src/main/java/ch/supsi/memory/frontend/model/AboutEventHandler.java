package ch.supsi.memory.frontend.model;

import java.time.LocalDate;
import java.util.List;

public interface AboutEventHandler {

    String getTitleKey();

    String getFrontendName();

    String getVersionFrontend();

    String getVersionFrontendKey();

    LocalDate getBuildDateFrontend();

    String getBuildDateFrontendKey();

    String getBackendName();

    String getVersionBackend();

    String getVersionBackendKey();

    LocalDate getBuildDateBackend();

    String getBuildDateBackendKey();

    String getDevelopersKey();

    List<String> getDevelopers();

    String getCopyrightKey();
}