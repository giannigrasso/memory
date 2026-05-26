package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.SaveAsCommand;
import ch.supsi.memory.frontend.controller.GameEventController;
import ch.supsi.memory.frontend.tui.CliFilePathProvider;

public class SaveAsCliCommandFactory implements CliCommandFactory {

    private final GameEventController gameController;
    private final CliFilePathProvider filePathProvider;

    public SaveAsCliCommandFactory(GameEventController gameController, CliFilePathProvider filePathProvider) {
        this.gameController = gameController;
        this.filePathProvider = filePathProvider;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        if (!this.gameController.isDirty()) {
            throw new CliCommandException("no new modifications", "label.tui.errors.not_dirty");
        }
        if (args == null || args.length != 1 || args[0].isBlank()) {
            throw new CliCommandException(getUsage());
        }

        this.filePathProvider.setArgs(args);
        return new SaveAsCommand(this.gameController);
    }

    @Override
    public String getUsage() {
        return "saveas <path>";
    }
}
