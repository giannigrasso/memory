package ch.supsi.memory.frontend.controller;

import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.view.DataView;

import java.util.List;

public class GameController implements GameEventController, PlayerEventController {

    private static GameController myself;

    final private GameModel gameModel;

    private List<DataView> views;

    private GameController () {
        this.gameModel = GameModel.getInstance();
    }

    public static GameController getInstance() {
        if (myself == null) {
            myself = new GameController();
        }

        return myself;
    }

    public void initialize(List<DataView> views) {
        this.views = views;
    }

    @Override
    public void newGame() {
        // do whatever you must do to start a new game

        // then update your views
        this.views.forEach(DataView::update);
    }

    @Override
    public void save() {
        // do whatever you must do to start a new game

        // then update your views
        this.views.forEach(DataView::update);
    }

    // add all the relevant methods to handle all those defined by the GameEventHandler interface
    // ...

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
