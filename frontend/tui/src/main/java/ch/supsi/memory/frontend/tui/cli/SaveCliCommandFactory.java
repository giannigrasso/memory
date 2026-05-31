package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.SaveCommand;
import ch.supsi.memory.frontend.controller.GameEventController;

public class SaveCliCommandFactory implements CliCommandFactory {

    private final GameEventController gameController;

    public SaveCliCommandFactory(GameEventController gameController) {
        this.gameController = gameController;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        if (args != null && args.length != 0) {
            throw new CliCommandException(getUsage());
        }
        if (!this.gameController.isDirty()) {
            throw new CliCommandException("no new modifications", "label.errors.not_dirty");
        }
        if (!this.gameController.hasFilePath()) {
            throw new CliCommandException("game has no save path", "label.errors.no_path");
        }

        return new SaveCommand(this.gameController);
    }

    @Override
    public String getUsage() {
        return "save";
    }
}
