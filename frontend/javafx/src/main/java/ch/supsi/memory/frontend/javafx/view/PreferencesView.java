package ch.supsi.memory.frontend.javafx.view;

import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.command.EditPreferencesCommand;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.PreferencesEventHandler;
import ch.supsi.memory.frontend.model.TranslationProvider;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Properties;

// TODO:
public class PreferencesView implements ControlledFxView {

    private static PreferencesView myself;

    private PreferencesEventHandler prefsModel;

    private final Dialog<Properties> dialog = new Dialog<>();
    private final ComboBox<String> langCombo = new ComboBox<>();
    private final ComboBox<String> batchCombo = new ComboBox<>();
    private final Label langLabel = new Label();
    private final Label batchSizeLabel = new Label();
    private final VBox layout = new VBox(
            15,
            new HBox(10, langLabel, langCombo),
            new HBox(10, batchSizeLabel, batchCombo));

    protected PreferencesView() {
        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK,
                ButtonType.CANCEL
        );
        dialog.getDialogPane().setContent(layout);
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                Properties props = new Properties();
                props.setProperty("locale", langCombo.getValue());
                props.setProperty("batch_size", batchCombo.getValue());
                return props;
            }
            return null;
        });
    }

    public static PreferencesView getInstance() {
        if (myself == null) {
            myself = new PreferencesView();
        }

        return myself;
    }

    @Override
    public Node getNode() {
        return this.dialog.getDialogPane();
    }

    @Override
    public void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator) {
        this.prefsModel = (PreferencesEventHandler) model;

        this.dialog.resultProperty().addListener((obs, oldVal, props) -> {
            if (props == null) return;

            EditPreferencesCommand cmd = commands.get(EditPreferencesCommand.class);
            cmd.setLocale(this.langCombo.getValue());
            // TODO: handle error??
            cmd.setBatchSize(Integer.parseInt(this.batchCombo.getValue()));
            cmd.execute();
        });

        this.applyTranslations(translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        dialog.setTitle(translator.translate("label.editprefs.popup.title"));

        langLabel.setText(translator.translate("label.editprefs.label.language"));
        batchSizeLabel.setText(translator.translate("label.editprefs.label.batch_size"));

        // TODO: refac hardcoded
        langCombo.getItems().addAll("it-CH", "en-US");

        // TODO: refac hardcoded
        batchCombo.getItems().addAll("2", "4", "6", "8");
    }

    @Override
    public void update() {
        langCombo.setValue(this.prefsModel.getLocale());
        batchCombo.setValue("" + this.prefsModel.getBatchSize());

        if (this.prefsModel.isShowing()) {
            this.dialog.showAndWait();
        }
    }
}
