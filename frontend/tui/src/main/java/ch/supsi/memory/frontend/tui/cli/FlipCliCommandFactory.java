package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.FlipCommand;
import ch.supsi.memory.frontend.controller.GameEventController;

public class FlipCliCommandFactory implements CliCommandFactory {

    private final GameEventController gameController;

    public FlipCliCommandFactory(GameEventController gameController) {
        this.gameController = gameController;
    }

    @Override
    public Command create(String[] args) throws CliCommandException {
        final int x, y;

        if (args.length != 2) {
            throw new CliCommandException(getUsage());
        }

        try {
            x = Integer.parseInt(args[0]);
            y = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new CliCommandException(getUsage(), e);
        }

        FlipCommand cmd = new FlipCommand(this.gameController);
        cmd.setCell(x, y);
       
        return cmd;
    }

    @Override
    public String getUsage() {
        return "flip <x:int> <y:int>";
    }
}
