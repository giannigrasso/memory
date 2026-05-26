package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.LoadGameCommand;
import ch.supsi.memory.frontend.controller.GameEventController;
import ch.supsi.memory.frontend.tui.CliFilePathProvider;

public class LoadGameCliCommandFactory implements CliCommandFactory {

    private final GameEventController gameController;
    private final CliFilePathProvider filePathProvider;

    public LoadGameCliCommandFactory(GameEventController gameController, CliFilePathProvider filePathProvider) {
        this.gameController = gameController;
        this.filePathProvider = filePathProvider;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        if (args == null || args.length != 1 || args[0].isBlank()) {
            throw new CliCommandException(getUsage());
        }

        this.filePathProvider.setArgs(args);
        return new LoadGameCommand(this.gameController);
    }

    @Override
    public String getUsage() {
        return "load <path>";
    }
}
