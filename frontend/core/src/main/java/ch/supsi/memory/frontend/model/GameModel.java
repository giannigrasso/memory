package ch.supsi.memory.frontend.model;

import ch.supsi.memory.backend.application.GameController;
import ch.supsi.memory.backend.model.GameObject;

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
        this.noGameLoaded = false;

        return true;
    }

    @Override
    public boolean save() {
        return this.backend.save(this.filePath);
    }

    @Override
    public boolean load(Path path) {
        boolean result = this.backend.load(path) != null;

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
        // TODO: handle out of bounds exception
        if (this.backend.flip(coords)) {
            // ...
        }

        return true;
    }

    public boolean isDirty() {
        return this.backend.isDirty();
    }

    @Override
    public int getBatchSize() {
        return this.backend.getBatchSize();
    }

    public boolean isNoGameLoaded() {
        return this.noGameLoaded;
    }

    public int getCurrentTurnFlippedCount() {
        return this.backend.getCurrentTurnFlippedCount();
    }

    @Override
    public boolean isSafeToQuit() {
        return !this.isDirty();
    }
}
