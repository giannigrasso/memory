package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.GameEventController;

import java.util.function.Supplier;

public class NewGameCommand implements Command {

    private final GameEventController receiver;
    private final Supplier<Integer> batchSizeProvider;

    public NewGameCommand(GameEventController receiver, Supplier<Integer> batchSizeProvider) {
        this.receiver = receiver;
        this.batchSizeProvider = batchSizeProvider;
    }

    @Override
    public void execute() {
        this.receiver.newGame(this.batchSizeProvider.get());
    }
}
