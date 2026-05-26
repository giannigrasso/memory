package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.UserFeedbackEventController;

public class FeedbackCommand implements Command {

    private final UserFeedbackEventController receiver;
    private String text;

    public FeedbackCommand(UserFeedbackEventController receiver) {
        this.receiver = receiver;
        this.text = "";
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void execute() {
        this.receiver.publish(this.text);
    }
}
