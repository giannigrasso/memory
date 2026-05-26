package ch.supsi.memory.frontend.controller;

public class QuitMediatorImpl implements QuitMediator {

    private QuitDirector quitDirector;

    @Override
    public void registerQuitDirector(QuitDirector director) {
        this.quitDirector = director;
    }

    @Override
    public void onQuitRequested() {
        if (this.quitDirector == null) {
            throw new IllegalStateException("No QuitDirector registered");
        }

        this.quitDirector.handleQuitRequest();
    }
}
