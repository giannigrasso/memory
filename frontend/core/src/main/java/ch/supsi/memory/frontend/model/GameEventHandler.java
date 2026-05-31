package ch.supsi.memory.frontend.model;

import java.nio.file.Path;
import java.util.Optional;

public interface GameEventHandler extends EventHandler {

    boolean flip(int[] coords);

    boolean newGame(int batchSize);

    boolean save();

    boolean load(Path path);

    void setFilePath(Path filePath);

    Optional<Path> getFilePath();

    boolean hasFilePath();

    boolean isDirty();

    int getBatchSize();

    int getGridWidth();

    int getGridHeight();

    int getCurrentTurnFlippedCount();
}
