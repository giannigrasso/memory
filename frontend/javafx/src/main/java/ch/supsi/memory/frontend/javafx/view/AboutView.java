package ch.supsi.memory.frontend.javafx.view;

import ch.supsi.memory.frontend.model.AboutEventHandler;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;

public class AboutView implements UncontrolledFxView {

    private static AboutView myself;

    private AboutEventHandler aboutModel;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
    private final VBox content = new VBox(10);

    // header
    private final Label nameLabel = new Label();

    // frontend section
    private final Label frontendTitleLabel = new Label();
    private final Label frontendVersionLabel = new Label();
    private final Label frontendBuildLabel = new Label();

    // backend section
    private final Label backendTitleLabel = new Label();
    private final Label backendVersionLabel = new Label();
    private final Label backendBuildLabel = new Label();

    // footer
    private final Label devsTitleLabel = new Label();
    private final Label devsContentLabel = new Label();
    private final Label copyrightLabel = new Label();

    protected AboutView() {
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        frontendTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        backendTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        devsTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        copyrightLabel.setStyle("-fx-font-size: 0.9em; -fx-text-fill: gray;");

        content.setPadding(new Insets(25, 20, 10, 25));
        content.getChildren().addAll(
                nameLabel,
                new Label(""),
                frontendTitleLabel,
                frontendVersionLabel,
                frontendBuildLabel,
                new Separator(),
                backendTitleLabel,
                backendVersionLabel,
                backendBuildLabel,
                new Separator(),
                devsTitleLabel,
                devsContentLabel,
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
        aboutDialog.setTitle(translator.translate(aboutModel.getTitleKey()));

        nameLabel.setText(aboutModel.getFrontendName());

        frontendTitleLabel.setText("Frontend");
        frontendVersionLabel.setText(translator.translate(aboutModel.getVersionFrontendKey()) + aboutModel.getVersionFrontend());
        frontendBuildLabel.setText(translator.translate(aboutModel.getBuildDateFrontendKey()) + aboutModel.getBuildDateFrontend().format(DATE_FORMATTER));

        backendTitleLabel.setText("Backend");
        backendVersionLabel.setText(translator.translate(aboutModel.getVersionBackendKey()) + aboutModel.getVersionBackend());
        backendBuildLabel.setText(translator.translate(aboutModel.getBuildDateBackendKey()) + aboutModel.getBuildDateBackend().format(DATE_FORMATTER));

        devsTitleLabel.setText(translator.translate(aboutModel.getDevelopersKey()));
        final StringBuilder devs = new StringBuilder();
        for (String dev : aboutModel.getDevelopers()) {
            devs.append(dev).append("\n");
        }
        devs.deleteCharAt(devs.length() - 1);
        devsContentLabel.setText(devs.toString());

        copyrightLabel.setText(translator.translate(aboutModel.getCopyrightKey()));
    }

    @Override
    public void update() {
        this.aboutDialog.showAndWait();
    }
}