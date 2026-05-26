package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.ShowPreferencesCommand;
import ch.supsi.memory.frontend.controller.PreferencesEventController;

public class ShowPreferencesCliCommandFactory implements CliCommandFactory {

    private final PreferencesEventController preferencesController;

    public ShowPreferencesCliCommandFactory(PreferencesEventController preferencesController) {
        this.preferencesController = preferencesController;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        return new ShowPreferencesCommand(this.preferencesController);
    }

    @Override
    public String getUsage() {
        return "prefs";
    }
}
