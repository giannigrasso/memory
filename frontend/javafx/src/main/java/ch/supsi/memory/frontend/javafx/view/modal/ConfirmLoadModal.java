package ch.supsi.memory.frontend.javafx.view.modal;

import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmLoadModal implements UserConfirmationProvider {

    private final TranslationProvider translator;

    public ConfirmLoadModal(TranslationProvider translator) {
        this.translator = translator;
    }

    @Override
    public boolean confirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(this.translator.translate("label.confirm_load.title"));
        alert.setHeaderText(this.translator.translate("label.confirm_load.text"));

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
