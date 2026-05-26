package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.GameEventController;

public class FlipCommand implements Command {

    final private GameEventController receiver;

    private int[] coords;

    public FlipCommand(GameEventController receiver) {
        this.receiver = receiver;
    }

    public void setCell(int x, int y) {
        this.coords = new int[]{x, y};
    }

    @Override
    public void execute() {
        receiver.flip(coords);
    }

}
