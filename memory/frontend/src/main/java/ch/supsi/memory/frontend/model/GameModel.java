package ch.supsi.memory.frontend.model;

import ch.supsi.memory.backend.application.GameController;

public class GameModel extends AbstractModel implements GameEventHandler, PlayerEventHandler {

    private static GameModel myself;

    final private GameController backend;

    private int[] lastFlippedCell;

    protected GameModel() {
        super();
        this.backend = GameController.getInstance();
    }

    public static GameModel getInstance() {
        if (myself == null) {
            myself = new GameModel();
        }

        return myself;
    }

    @Override
    public boolean newGame() {
        // ...

        return true;
    }

    @Override
    public boolean save() {
        // ...

        return true;
    }

    @Override
    public boolean flip(int[] coords) {
        // set last flipped cell
        this.lastFlippedCell = new int[] {coords[0], coords[1]};

        // delegate the work to the backend
        if (this.backend.flip(coords)) {
            // for demo purpose we print the cell coordinates to stdout
            System.out.println("last flipped cell...(" + lastFlippedCell[0] + "," + lastFlippedCell[1] + ")");
        }

        // return
        return true;
    }

}
