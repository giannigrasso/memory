package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.PlayerEventController;

public class FlipCommand implements Command {

    final private PlayerEventController receiver;

    private int[] coords;

    public FlipCommand(PlayerEventController receiver) {
        this.receiver = receiver;
    }

    public void setCell(int x, int y) {
        this.coords = new int[] {x, y};
    }

    @Override
    public void execute() {
        receiver.flip(coords);
    }

}
