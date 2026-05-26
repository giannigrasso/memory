package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.GameEventController;

public class SaveAsCommand implements Command {

    private final GameEventController receiver;

    public SaveAsCommand(GameEventController receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.saveAs();
    }
}
