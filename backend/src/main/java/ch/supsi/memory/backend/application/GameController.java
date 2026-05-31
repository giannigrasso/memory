package ch.supsi.memory.backend.application;

import ch.supsi.memory.backend.business.BusinessException;
import ch.supsi.memory.backend.business.GameServiceImpl;
import ch.supsi.memory.backend.business.validate.GridCoordinatesRule;
import ch.supsi.memory.backend.model.GameModel;
import ch.supsi.memory.backend.model.GameObject;

import java.nio.file.Path;

public class GameController {

    private static GameController myself;

    private final GameService gameService;

    protected GameController() {
        this.gameService = GameServiceImpl.getInstance();
    }

    public static GameController getInstance() {
        if (myself == null) {
            myself = new GameController();
        }

        return myself;
    }

    public boolean flip(final int[] coords) {
        if (coords == null) {
            throw new BackendException("bad coordinates: null");
        }

        if (!GridCoordinatesRule.isValid(coords)) {
            final String errorMsg = String.format(
                    "flip coordinates (%d, %d) out of bounds",
                    coords[1],
                    coords[0]);
            throw new BackendException(errorMsg);
        }

        try {
            return gameService.flip(coords);
        } catch (BusinessException e) {
            throw new BackendException("game not loaded", e);
        }
    }

    public void newGame(int batchSize) {
        this.gameService.newGame(batchSize);
    }

    public boolean isDirty() {
        return this.gameService.isDirty();
    }

    public boolean save(Path path) {
        if (path == null) {
            throw new BackendException("bad path: null");
        }

        try {
            return this.gameService.save(path);
        } catch (BusinessException e) {
            throw new BackendException("failed to save", e);
        }
    }

    public GameModel load(Path path) {
        if (path == null) {
            throw new BackendException("bad path: null");
        }

        try {
            return this.gameService.load(path);
        } catch (BusinessException e) {
            throw new BackendException("failed to load game", e);
        }
    }

    public GameObject getAt(int[] coords) {
        if (coords == null) {
            throw new BackendException("bad coords: null");
        }

        return this.gameService.getAt(coords);
    }

    public int getCurrentTurnFlippedCount() {
        return this.gameService.getCurrentTurnFlippedCount();
    }

    public int getBatchSize() {
        return this.gameService.getBatchSize();
    }

    public int getGridCoordinateX() {
        return gameService.getGridCoordinateX();
    }

    public int getGridCoordinateY() {
        return gameService.getGridCoordinateY();
    }
}
