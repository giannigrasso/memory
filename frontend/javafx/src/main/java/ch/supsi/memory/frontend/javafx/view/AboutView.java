package ch.supsi.memory.frontend.javafx.view;

import ch.supsi.memory.frontend.model.AboutEventHandler;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AboutView implements UncontrolledFxView {

    private static AboutView myself;

    private AboutEventHandler aboutModel;

    private final Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
    private final VBox content = new VBox(10);
    private final Label nameLabel = new Label();
    private final Label versionLabel = new Label();
    private final Label buildLabel = new Label();
    private final Label devLabel = new Label();
    private final Label copyrightLabel = new Label();

    protected AboutView() {
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        copyrightLabel.setStyle("-fx-font-size: 0.9em; -fx-text-fill: gray;");

        content.setPadding(new Insets(25, 20, 10, 25));
        content.getChildren().addAll(
                nameLabel,
                versionLabel,
                buildLabel,
                devLabel,
                new Label(""),
                copyrightLabel
        );

        aboutDialog.setHeaderText(null);
        aboutDialog.getDialogPane().setContent(content);
    }

    public static AboutView getInstance() {
        if (myself == null) {
            myself = new AboutView();
        }

        return myself;
    }

    @Override
    public Node getNode() {
        return aboutDialog.getDialogPane();
    }

    @Override
    public void initialize(AbstractModel model, TranslationProvider translator) {
        this.aboutModel = (AboutEventHandler) model;
        this.applyTranslations(translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        nameLabel.setText("Memory GUI");
        versionLabel.setText(translator.translate("label.about.version") + "1.0.0-stable");
        buildLabel.setText("Build Date: 2026-04-28");
        devLabel.setText(translator.translate("label.about.developed_by"));
        copyrightLabel.setText(translator.translate("label.about.copyright"));
        aboutDialog.setTitle(translator.translate("label.about.title"));
    }

    @Override
    public void update() {
        this.aboutDialog.showAndWait();
    }
}
