package ch.supsi.memory.backend.application;

import ch.supsi.memory.backend.model.GameModel;
import ch.supsi.memory.backend.model.GameObject;

import java.nio.file.Path;

public interface GameService {

    void newGame(int batchSize);

    boolean flip(int[] coords);

    boolean isDirty();

    boolean save(Path path);

    GameModel load(Path path);

    GameObject getAt(int[] coords);

    int getCurrentTurnFlippedCount();

    int getBatchSize();
}
