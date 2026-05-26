package ch.supsi.memory.frontend.controller;

public interface QuitMediator {

    void onQuitRequested();

    void registerQuitDirector(QuitDirector director);
}
