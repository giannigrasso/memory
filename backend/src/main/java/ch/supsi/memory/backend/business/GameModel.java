package ch.supsi.memory.backend.business;

import ch.supsi.memory.backend.application.GameBusinessInterface;

public class GameModel implements GameBusinessInterface {

    private static GameModel myself;

    protected GameModel() {}

    public static GameModel getInstance() {
        if (myself == null) {
            myself = new GameModel();
        }

        return myself;
    }

    public boolean flip(int[] coords) {
        // for demo purposes we just return true
        return true;
    }

}
