package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.GameEventController;

public class LoadGameCommand implements Command {

    final private GameEventController receiver;

    public LoadGameCommand(GameEventController receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.load();
    }
}
