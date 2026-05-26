package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.HelpEventController;

public class HelpCommand implements Command {

    final private HelpEventController receiver;

    public HelpCommand(HelpEventController receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.showHelp();
    }
}
