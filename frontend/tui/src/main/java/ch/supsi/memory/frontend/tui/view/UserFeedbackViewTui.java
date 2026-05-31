package ch.supsi.memory.frontend.tui.view;

import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.FeedbackMessage;
import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.model.UserFeedbackModel;
import ch.supsi.memory.frontend.view.UncontrolledView;

public class UserFeedbackViewTui implements UncontrolledView {

    private static UserFeedbackViewTui myself;

    private UserFeedbackModel userFeedbackModel;

    private TranslationProvider translator;

    protected UserFeedbackViewTui() {
    }

    public static UserFeedbackViewTui getInstance() {
        if (myself == null) {
            myself = new UserFeedbackViewTui();
        }

        return myself;
    }

    @Override
    public void initialize(AbstractModel model, TranslationProvider translator) {
        this.userFeedbackModel = (UserFeedbackModel) model;
        this.translator = translator;
    }

    @Override
    public void update() {
        final FeedbackMessage feedback = this.userFeedbackModel.getCurrent();
        final String formatted = format(feedback);

        System.out.println(formatted);
    }

    private String format(FeedbackMessage msg) {
        return switch (msg.getType()) {
            case NEW_GAME -> translator.translate("label.feedback.new_game");
            case SAVE_OK -> {
                final String path = msg.getParam("path");
                final String template = translator.translate("label.feedback.saved_game");
                final String text = template
                        .replace("%path", path);

                yield text;
            }
            case SAVE_FAILED -> translator.translate("label.error.save_failed");
            case LOAD_OK -> translator.translate("label.feedback.loaded_game");
            case LOAD_FAILED -> translator.translate("label.error.load_failed");
            case BAD_FLIP -> translator.translate("label.error.bad_flip");
            case NO_GAME_LOADED ->
                    translator.translate("label.error.no_game_loaded");
            case UNKNOWN_ERROR -> translator.translate("label.error.unknown");
            case CLEAR -> "";

            case FLIPPED -> {
                final int count = msg.getParam("count");
                final int max = msg.getParam("max");
                final String template = translator.translate("label.flipped");
                final String text = template
                        .replace("%count", "" + count)
                        .replace("%max", "" + max);

                yield text;
            }
        };
    }
}
