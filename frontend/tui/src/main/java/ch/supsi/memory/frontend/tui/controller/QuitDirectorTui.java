package ch.supsi.memory.frontend.tui.controller;

import ch.supsi.memory.frontend.controller.QuitDirector;
import ch.supsi.memory.frontend.model.QuitEventHandler;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;

public class QuitDirectorTui implements QuitDirector {

    private final QuitEventHandler quitModel;

    private final UserConfirmationProvider confirmQuitProvider;

    private boolean mustQuit;

    public QuitDirectorTui(QuitEventHandler quitModel, UserConfirmationProvider confirmQuitProvider) {
        this.quitModel = quitModel;
        this.confirmQuitProvider = confirmQuitProvider;
        this.mustQuit = false;
    }

    private void doQuit() {
        this.mustQuit = true;
    }

    public boolean mustQuit() {
        return mustQuit;
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
