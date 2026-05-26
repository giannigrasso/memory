package ch.supsi.memory.frontend.model;

public interface UserFeedbackEventHandler extends EventHandler {

    void publish(String text);

    void clear();
}
