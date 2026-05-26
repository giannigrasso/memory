package ch.supsi.memory.frontend.controller;

public class QuitController implements QuitEventController {

    private static QuitController myself;

    private QuitMediator quitMediator;

    protected QuitController() {
    }

    public static QuitController getInstance() {
        if (myself == null) {
            myself = new QuitController();
        }

        return myself;
    }

    public void initialize(QuitMediator quitMediator) {
        this.quitMediator = quitMediator;
    }

    @Override
    public void quit() {
        this.quitMediator.onQuitRequested();
    }
}
