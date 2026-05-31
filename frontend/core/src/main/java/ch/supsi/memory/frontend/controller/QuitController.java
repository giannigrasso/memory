package ch.supsi.memory.frontend.controller;

public class QuitController implements QuitEventController {

    private static QuitController myself;

    private QuitDirector quitDirector;

    protected QuitController() {
    }

    public static QuitController getInstance() {
        if (myself == null) {
            myself = new QuitController();
        }

        return myself;
    }

    public void registerQuitDirector(QuitDirector director) {
        this.quitDirector = director;
    }

    @Override
    public void quit() {
        if (this.quitDirector == null) {
            throw new IllegalStateException("No QuitDirector registered");
        }

        this.quitDirector.handleQuitRequest();
    }
}
