package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.AboutCommand;
import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.controller.AboutEventController;

public class AboutCliCommandFactory implements CliCommandFactory {

    private final AboutEventController aboutController;

    public AboutCliCommandFactory(AboutEventController aboutController) {
        this.aboutController = aboutController;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        return new AboutCommand(this.aboutController);
    }

    @Override
    public String getUsage() {
        return "about";
    }
}
