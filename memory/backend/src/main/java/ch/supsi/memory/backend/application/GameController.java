package ch.supsi.memory.backend.application;

import ch.supsi.memory.backend.business.GameModel;

public class GameController {

    private static GameController myself;

    final private GameModel gameModel;

    protected GameController() {
        this.gameModel = GameModel.getInstance();
    }

    public static GameController getInstance() {
        if (myself == null) {
            myself = new GameController();
        }

        return myself;
    }

    public boolean flip(int[] coords) {
        return gameModel.flip(coords);
    }

}
