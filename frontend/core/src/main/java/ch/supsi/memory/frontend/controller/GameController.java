package ch.supsi.memory.frontend.controller;

import ch.supsi.memory.frontend.model.GameEventHandler;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.model.UserFeedbackEventHandler;
import ch.supsi.memory.frontend.model.UserFeedbackModel;
import ch.supsi.memory.frontend.view.DataView;
import ch.supsi.memory.frontend.view.FilePathProvider;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class GameController implements GameEventController {

    private static GameController myself;

    private final GameEventHandler gameModel;
    private final UserFeedbackEventHandler feedbackModel;

    private List<DataView> views;
    private DataView feedbackView;

    private UserConfirmationProvider confirmLoadProvider;
    private FilePathProvider loadPathProvider;
    private FilePathProvider savePathProvider;

    public GameController() {
        // DI would be better for sure....
        this.gameModel = GameModel.getInstance();
        this.feedbackModel = UserFeedbackModel.getInstance();
    }

    public void init(List<DataView> views, DataView feedbackView, UserConfirmationProvider confirmLoadProvider, FilePathProvider loadPathProvider, FilePathProvider savePathProvider) {
        this.views = views;
        this.feedbackView = feedbackView;
        this.confirmLoadProvider = confirmLoadProvider;
        this.loadPathProvider = loadPathProvider;
        this.savePathProvider = savePathProvider;
    }

    public static GameController getInstance() {
        if (myself == null) {
            myself = new GameController();
        }

        return myself;
    }

    @Override
    public boolean isDirty() {
        return this.gameModel.isDirty();
    }

    @Override
    public boolean hasFilePath() {
        return this.gameModel.hasFilePath();
    }

    @Override
    public void newGame(int batchSize) {
        if (this.isDirty()) {
            boolean confirm = this.confirmLoadProvider.confirm();
            if (!confirm) return;
        }

        if (this.gameModel.newGame(batchSize)) {
            this.feedbackModel.publishNewGame();
            this.views.forEach(DataView::update);
            this.feedbackView.update();
        } else {
            this.feedbackView.update();
        }
    }

    @Override
    public void save() {
        try {
            if (this.gameModel.save()) {
                // command runs only if model has path, therefore get will never fail.
                final String pathStr = this.gameModel.getFilePath().get().toString();
                this.feedbackModel.publishSaveOk(pathStr);
            } else {
                this.feedbackModel.publishSaveFailed();
            }
        } catch (GameOperationException e) {
            switch (e.getReason()) {
                case SAVE_FAILED -> this.feedbackModel.publishSaveFailed();
                default -> this.feedbackModel.publishUnknownError();
            }
        } finally {
            this.views.forEach(DataView::update);
            this.feedbackView.update();
        }
    }

    @Override
    public void saveAs() {
        Optional<Path> savePath = this.savePathProvider.getPath();
        if (savePath.isEmpty()) return;

        this.gameModel.setFilePath(savePath.get());
        this.save();
    }

    @Override
    public void load() {
        if (this.isDirty()) {
            boolean confirm = this.confirmLoadProvider.confirm();
            if (!confirm) return;
        }

        Optional<Path> loadPath = this.loadPathProvider.getPath();
        if (loadPath.isEmpty()) return;

        boolean rv = false;
        try {
            rv = this.gameModel.load(loadPath.get());
        } catch (GameOperationException e) {
            switch (e.getReason()) {
                case BAD_FLIP -> this.feedbackModel.publishBadFlip();
                case NO_GAME_LOADED -> this.feedbackModel.publishNoGameLoaded();
                default -> this.feedbackModel.publishUnknownError();
            }
        }

        if (rv) {
            this.feedbackModel.publishLoadOk();
            this.views.forEach(DataView::update);
        } else {
            this.feedbackModel.publishLoadFailed();
        }
        this.feedbackView.update();
    }

    @Override
    public void flip(int[] coords) {
        try {
            if (this.gameModel.flip(coords)) {
                final int flipped = this.gameModel.getCurrentTurnFlippedCount();
                final int bs = this.gameModel.getBatchSize();

                this.feedbackModel.publishFlipped(flipped, bs);
                views.forEach(DataView::update);
                this.feedbackView.update();
            } else {
                this.feedbackView.update();
            }
        } catch (GameOperationException e) {
            switch (e.getReason()) {
                case BAD_FLIP -> this.feedbackModel.publishBadFlip();
                case NO_GAME_LOADED -> this.feedbackModel.publishNoGameLoaded();
                default -> this.feedbackModel.publishUnknownError();
            }
            this.feedbackView.update();
        }
    }
}
