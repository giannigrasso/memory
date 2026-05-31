package ch.supsi.memory.frontend.command;


import ch.supsi.memory.frontend.controller.PreferencesEventController;

import java.util.Properties;

public class EditPreferencesCommand implements Command {

    private final PreferencesEventController receiver;

    private String locale;
    private int batchSize;

    public EditPreferencesCommand(PreferencesEventController receiver) {
        this.receiver = receiver;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public void execute() {
        final Properties properties = new Properties();
        properties.setProperty("locale", this.locale);
        properties.setProperty("batch_size", "" + this.batchSize);

        receiver.editPreferences(properties);
    }
}
