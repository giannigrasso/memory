package ch.supsi.memory.frontend.controller;

import ch.supsi.memory.frontend.model.GameEventHandler;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.view.DataView;
import ch.supsi.memory.frontend.view.FilePathProvider;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class GameController implements GameEventController {

    private static GameController myself;

    final private GameEventHandler gameModel;

    private List<DataView> views;

    private UserConfirmationProvider confirmLoadProvider;
    private FilePathProvider loadPathProvider;
    private FilePathProvider savePathProvider;

    public GameController() {
        this.gameModel = GameModel.getInstance();
    }

    public void init(List<DataView> views, UserConfirmationProvider confirmLoadProvider, FilePathProvider loadPathProvider, FilePathProvider savePathProvider) {
        this.views = views;
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

        // do whatever you must do to start a new game
        this.gameModel.setFilePath(null);
        this.gameModel.newGame(batchSize);

        // then update your views
        this.views.forEach(DataView::update);
    }

    @Override
    public void save() {
        this.gameModel.save();

        this.views.forEach(DataView::update);
    }

    @Override
    public void saveAs() {
        Optional<Path> savePath = this.savePathProvider.getPath();
        if (savePath.isEmpty()) return;

        this.gameModel.setFilePath(savePath.get());
        this.save();

        this.views.forEach(DataView::update);
    }

    @Override
    public void load() {
        if (this.isDirty()) {
            boolean confirm = this.confirmLoadProvider.confirm();
            if (!confirm) return;
        }

        Optional<Path> loadPath = this.loadPathProvider.getPath();
        if (loadPath.isEmpty()) return;

        if (this.gameModel.load(loadPath.get())) {
            this.views.forEach(DataView::update);
        }
    }

    @Override
    public void flip(int[] coords) {
        if (this.gameModel.flip(coords)) {
            views.forEach(DataView::update);
        } else {
            // handle error
            return;
        }
    }
}
