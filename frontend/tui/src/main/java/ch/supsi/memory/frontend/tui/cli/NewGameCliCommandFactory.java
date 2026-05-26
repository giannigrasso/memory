package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.NewGameCommand;
import ch.supsi.memory.frontend.controller.GameEventController;
import ch.supsi.memory.frontend.controller.PreferencesEventController;

public class NewGameCliCommandFactory implements CliCommandFactory {

    private final GameEventController gameController;
    private final PreferencesEventController preferencesController;

    public NewGameCliCommandFactory(GameEventController gameController, PreferencesEventController preferencesController) {
        this.gameController = gameController;
        this.preferencesController = preferencesController;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        return new NewGameCommand(this.gameController, this.preferencesController::getBatchSize);
    }

    @Override
    public String getUsage() {
        return "new";
    }
}
