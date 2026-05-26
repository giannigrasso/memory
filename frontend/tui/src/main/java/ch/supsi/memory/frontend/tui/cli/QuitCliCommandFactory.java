package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.QuitCommand;
import ch.supsi.memory.frontend.controller.QuitEventController;

public class QuitCliCommandFactory implements CliCommandFactory {

    private final QuitEventController quitController;

    public QuitCliCommandFactory(QuitEventController quitController) {
        this.quitController = quitController;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        return new QuitCommand(this.quitController);
    }

    @Override
    public String getUsage() {
        return "quit";
    }
}
