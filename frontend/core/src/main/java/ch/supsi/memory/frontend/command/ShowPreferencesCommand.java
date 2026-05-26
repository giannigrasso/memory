package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.PreferencesEventController;

public class ShowPreferencesCommand implements Command {

    private final PreferencesEventController receiver;

    public ShowPreferencesCommand(PreferencesEventController receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.showPreferences();
    }
}
