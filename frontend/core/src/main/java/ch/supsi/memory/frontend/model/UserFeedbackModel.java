package ch.supsi.memory.frontend.model;

public class UserFeedbackModel extends AbstractModel implements UserFeedbackEventHandler {

    private static UserFeedbackModel myself;

    private FeedbackMessage current = FeedbackMessage.clear();

    public UserFeedbackModel() {
    }

    public static UserFeedbackModel getInstance() {
        if (myself == null) {
            myself = new UserFeedbackModel();
        }
        return myself;
    }

    @Override
    public void publishNewGame() {
        this.current = FeedbackMessage.newGame();
    }

    @Override
    public void publishSaveOk(String path) {
        this.current = FeedbackMessage.saveOk(path);
    }

    @Override
    public void publishLoadOk() {
        this.current = FeedbackMessage.loadOk();
    }

    @Override
    public void publishFlipped(int count, int max) {
        this.current = FeedbackMessage.flipped(count, max);
    }

    @Override
    public void publishUnknownError() {
        this.current = FeedbackMessage.unknownError();
    }

    @Override
    public void publishBadFlip() {
        this.current = FeedbackMessage.badFlip();
    }

    @Override
    public void publishNoGameLoaded() {
        this.current = FeedbackMessage.noGameLoaded();
    }

    @Override
    public void publishSaveFailed() {
        this.current = FeedbackMessage.saveFailed();
    }

    @Override
    public void publishLoadFailed() {
        this.current = FeedbackMessage.loadFailed();
    }

    @Override
    public void clear() {
        this.current = FeedbackMessage.clear();
    }

    @Override
    public FeedbackMessage getCurrent() {
        return this.current;
    }
}