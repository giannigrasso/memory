package ch.supsi.memory.frontend.command;


import ch.supsi.memory.frontend.controller.PreferencesEventController;

import java.util.Properties;

public class EditPreferencesCommand implements Command {

    final private PreferencesEventController receiver;

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
        System.out.printf("locale is %s%n", this.locale);
        properties.setProperty("locale", this.locale);
        System.out.printf("batch is %d%n", this.batchSize);
        properties.setProperty("batch_size", "" + this.batchSize);

        receiver.editPreferences(properties);
    }
}
