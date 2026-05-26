package ch.supsi.memory.frontend.controller;

public interface GameEventController extends EventController {

    void flip(int[] coords);

    void newGame(int batchSize);

    void save();

    void saveAs();

    void load();

    boolean isDirty();

    boolean hasFilePath();
}
