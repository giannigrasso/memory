package ch.supsi.memory.frontend.model;

public class UserFeedbackModel extends AbstractModel implements UserFeedbackEventHandler {

    private static UserFeedbackModel myself;

    private String feedbackText;

    public UserFeedbackModel() {
    }

    public static UserFeedbackModel getInstance() {
        if (myself == null) {
            myself = new UserFeedbackModel();
        }

        return myself;
    }

    @Override
    public void publish(String text) {
        this.feedbackText = text;
    }

    @Override
    public void clear() {
        this.feedbackText = "";
    }

    public String getFeedback() {
        return feedbackText;
    }
}
