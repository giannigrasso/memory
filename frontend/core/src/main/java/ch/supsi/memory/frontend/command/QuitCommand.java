package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.QuitEventController;

public class QuitCommand implements Command {

    private final QuitEventController receiver;

    public QuitCommand(QuitEventController receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.quit();
    }
}
