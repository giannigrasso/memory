package ch.supsi.memory.frontend.model;

import ch.supsi.memory.backend.application.BackendException;
import ch.supsi.memory.backend.application.GameController;
import ch.supsi.memory.backend.model.GameObject;
import ch.supsi.memory.frontend.controller.GameOperationException;

import java.nio.file.Path;
import java.util.Optional;

public class GameModel extends AbstractModel implements GameEventHandler, QuitEventHandler {

    private static GameModel myself;

    final private GameController backend;

    private Path filePath;

    private boolean noGameLoaded;

    protected GameModel() {
        super();
        this.backend = GameController.getInstance();

        this.filePath = null;
        this.noGameLoaded = true;
    }

    public static GameModel getInstance() {
        if (myself == null) {
            myself = new GameModel();
        }

        return myself;
    }

    public GameObject getAt(int[] coords) {
        return this.backend.getAt(coords);
    }

    @Override
    public boolean newGame(int batchSize) {
        this.backend.newGame(batchSize);
        this.setFilePath(null);
        this.noGameLoaded = false;

        return true;
    }

    @Override
    public boolean save() {
        try {
            return this.backend.save(this.filePath);
        } catch (BackendException e) {
            throw new GameOperationException(
                    GameOperationException.Reason.SAVE_FAILED,
                    "failed to save",
                    e);
        }
    }

    @Override
    public boolean load(Path path) {
        final boolean result;
        try {
            result = this.backend.load(path) != null;
        } catch (BackendException e) {
            throw new GameOperationException(
                    GameOperationException.Reason.LOAD_FAILED,
                    "failed to load game",
                    e);
        }

        if (result) {
            this.filePath = path;
            this.noGameLoaded = false;
        }

        return result;
    }

    @Override
    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Path> getFilePath() {
        return Optional.ofNullable(this.filePath);
    }

    @Override
    public boolean hasFilePath() {
        return this.filePath != null;
    }

    @Override
    public boolean flip(int[] coords) {
        try {
            return this.backend.flip(coords);
        } catch (BackendException e) {
            // meh...
            // would be better if we had this differentiation at Exception level imo
            if (this.noGameLoaded) {
                throw new GameOperationException(
                        GameOperationException.Reason.NO_GAME_LOADED,
                        "no game loaded",
                        e);
            }

            throw new GameOperationException(
                    GameOperationException.Reason.BAD_FLIP,
                    "bad flip",
                    e);
        }
    }

    public boolean isDirty() {
        return this.backend.isDirty();
    }

    @Override
    public int getBatchSize() {
        return this.backend.getBatchSize();
    }

    @Override
    public int getGridWidth() {
        return backend.getGridCoordinateX();
    }

    @Override
    public int getGridHeight() {
        return backend.getGridCoordinateY();
    }

    public boolean isNoGameLoaded() {
        return this.noGameLoaded;
    }

    @Override
    public int getCurrentTurnFlippedCount() {
        return this.backend.getCurrentTurnFlippedCount();
    }

    @Override
    public boolean isSafeToQuit() {
        return !this.isDirty();
    }


}
