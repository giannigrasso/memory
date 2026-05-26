package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.HelpCommand;
import ch.supsi.memory.frontend.controller.HelpEventController;

public class HelpCliCommandFactory implements CliCommandFactory {

    private final HelpEventController helpController;

    public HelpCliCommandFactory(HelpEventController helpController) {
        this.helpController = helpController;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        return new HelpCommand(this.helpController);
    }

    @Override
    public String getUsage() {
        return "help";
    }
}
