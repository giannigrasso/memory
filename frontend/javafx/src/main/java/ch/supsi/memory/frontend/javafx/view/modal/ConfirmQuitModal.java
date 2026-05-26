package ch.supsi.memory.frontend.javafx.view.modal;

import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmQuitModal implements UserConfirmationProvider {

    private final TranslationProvider translator;

    public ConfirmQuitModal(TranslationProvider translator) {
        this.translator = translator;
    }

    @Override
    public boolean confirm() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(this.translator.translate("label.confirm_quit.title"));
        alert.setHeaderText(this.translator.translate("label.confirm_quit.text"));

        final Optional<ButtonType> rv = alert.showAndWait();
        return rv.isPresent() && rv.get() == ButtonType.OK;
    }
}
