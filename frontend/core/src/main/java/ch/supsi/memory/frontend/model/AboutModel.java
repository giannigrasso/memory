package ch.supsi.memory.frontend.model;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class AboutModel extends AbstractModel implements AboutEventHandler {

    private static AboutModel myself;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Properties frontendProperties;
    private final Properties backendProperties;

    protected AboutModel() {
        this.frontendProperties = this.loadProperties("/app-frontend.properties");
        this.backendProperties = this.loadProperties("/app-backend.properties");
    }

    public static AboutModel getInstance() {
        if (myself == null) {
            myself = new AboutModel();
        }
        return myself;
    }

    private Properties loadProperties(String path) {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            System.err.println("failed to load " + path);
        }
        return props;
    }

    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value, FORMATTER);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    @Override
    public String getTitleKey() {
        return "label.about.title";
    }

    @Override
    public String getFrontendName() {
        return this.frontendProperties.getProperty("frontend.name", "Memory");
    }

    @Override
    public String getVersionFrontend() {
        return this.frontendProperties.getProperty("frontend.version", "N/A");
    }

    @Override
    public String getVersionFrontendKey() {
        return "label.about.version";
    }

    @Override
    public LocalDate getBuildDateFrontend() {
        return this.parseDate(this.frontendProperties.getProperty("frontend.build.date", ""));
    }

    @Override
    public String getBuildDateFrontendKey() {
        return "label.about.build_date";
    }

    @Override
    public String getBackendName() {
        return this.backendProperties.getProperty("backend.name", "Backend");
    }

    @Override
    public String getVersionBackend() {
        return this.backendProperties.getProperty("backend.version", "N/A");
    }

    @Override
    public String getVersionBackendKey() {
        return "label.about.version";
    }

    @Override
    public LocalDate getBuildDateBackend() {
        return this.parseDate(this.backendProperties.getProperty("backend.build.date", ""));
    }

    @Override
    public String getBuildDateBackendKey() {
        return "label.about.build_date";
    }

    @Override
    public String getDevelopersKey() {
        return "label.about.developed_by";
    }

    @Override
    public List<String> getDevelopers() {
        return Arrays.stream(this.frontendProperties.getProperty("developers")
                        .split(","))
                .map(String::trim)
                .toList();
    }

    @Override
    public String getCopyrightKey() {
        return "label.about.copyright";
    }
}