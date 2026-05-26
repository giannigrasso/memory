package ch.supsi.memory.frontend.javafx.controller;

import ch.supsi.memory.frontend.controller.QuitDirector;
import ch.supsi.memory.frontend.model.QuitEventHandler;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;
import javafx.stage.Stage;

public class QuitDirectorJfx implements QuitDirector {

    private final Stage stage;

    private final QuitEventHandler quitModel;

    private final UserConfirmationProvider confirmQuitProvider;

    public QuitDirectorJfx(QuitEventHandler quitModel, UserConfirmationProvider confirmQuitProvider, Stage stage) {
        this.quitModel = quitModel;
        this.confirmQuitProvider = confirmQuitProvider;
        this.stage = stage;
    }

    private void doQuit() {
        this.stage.close();
    }

    @Override
    public void handleQuitRequest() {
        if (!quitModel.isSafeToQuit()) {
            if (this.confirmQuitProvider.confirm()) {
                doQuit();
            }
        } else {
            doQuit();
        }
    }
}
