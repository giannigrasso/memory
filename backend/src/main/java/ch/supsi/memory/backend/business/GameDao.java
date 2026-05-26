package ch.supsi.memory.backend.business;

import ch.supsi.memory.backend.model.GameModel;

import java.nio.file.Path;

public interface GameDao {

    GameModel load(Path saveFilePath);

    boolean write(GameModel model, Path saveFilePath);
}
