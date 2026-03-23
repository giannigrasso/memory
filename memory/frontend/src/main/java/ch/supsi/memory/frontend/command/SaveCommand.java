package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.GameEventController;

public class SaveCommand implements Command {

    final private GameEventController receiver;

    public SaveCommand(GameEventController receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.save();
    }

}
