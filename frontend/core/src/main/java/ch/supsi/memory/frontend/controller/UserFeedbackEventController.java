package ch.supsi.memory.frontend.controller;

public interface UserFeedbackEventController extends EventController {

    void publish(String text);

    void clear();
}
