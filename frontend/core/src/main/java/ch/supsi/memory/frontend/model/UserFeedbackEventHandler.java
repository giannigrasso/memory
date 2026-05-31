package ch.supsi.memory.frontend.model;

public interface UserFeedbackEventHandler extends EventHandler {

    FeedbackMessage getCurrent();

    void publishNewGame();

    void publishSaveOk(String path);

    void publishLoadOk();

    void publishFlipped(int count, int max);

    void publishUnknownError();

    void publishBadFlip();

    void publishNoGameLoaded();

    void publishSaveFailed();

    void publishLoadFailed();

    void clear();
}
