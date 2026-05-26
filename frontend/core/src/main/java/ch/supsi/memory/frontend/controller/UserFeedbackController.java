package ch.supsi.memory.frontend.controller;

import ch.supsi.memory.frontend.model.UserFeedbackEventHandler;
import ch.supsi.memory.frontend.model.UserFeedbackModel;
import ch.supsi.memory.frontend.view.DataView;

import java.util.List;

public class UserFeedbackController implements UserFeedbackEventController {

    private static UserFeedbackController myself;

    private final UserFeedbackEventHandler userFeedbackModel;

    private List<DataView> views;

    protected UserFeedbackController() {
        this.userFeedbackModel = UserFeedbackModel.getInstance();
    }

    public static UserFeedbackController getInstance() {
        if (myself == null) {
            myself = new UserFeedbackController();
        }

        return myself;
    }

    public void initialize(List<DataView> views) {
        this.views = views;
    }

    @Override
    public void publish(String text) {
        if (text == null) return;

        this.userFeedbackModel.publish(text);

        views.forEach(DataView::update);
    }

    @Override
    public void clear() {
        this.userFeedbackModel.clear();

        views.forEach(DataView::update);
    }
}
