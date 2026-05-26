package ch.supsi.memory.frontend.javafx.view;

import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.HelpEventHandler;
import ch.supsi.memory.frontend.model.TranslationProvider;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HelpView implements UncontrolledFxView {

    private static HelpView myself;

    private HelpEventHandler helpModel;

    private final Alert helpDialog = new Alert(Alert.AlertType.INFORMATION);
    private final VBox content = new VBox(14);
    private final Label titleLabel = new Label();
    private final Label rulesTitle = new Label();
    private final Label rulesContent = new Label();
    private final Label featuresTitle = new Label();
    private final Label featuresContent = new Label();
    private final Label howTitle = new Label();
    private final Label howContent = new Label();

    protected HelpView() {
        helpDialog.setHeaderText(null);
        content.setPadding(new Insets(25, 20, 10, 25));

        // Titolo
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Regole del gioco
        rulesTitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        rulesContent.setWrapText(true);

        // Feature
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        featuresContent.setWrapText(true);

        // Come interagire
        howTitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        howContent.setWrapText(true);

        content.getChildren().addAll(
                titleLabel,
                new Label(""),
                rulesTitle,
                rulesContent,
                new Label(""),
                featuresTitle,
                featuresContent,
                new Label(""),
                howTitle,
                howContent
        );

        helpDialog.getDialogPane().setContent(content);
        helpDialog.getDialogPane().setPrefWidth(420);
    }

    public static HelpView getInstance() {
        if (myself == null) {
            myself = new HelpView();
        }

        return myself;
    }

    @Override
    public Node getNode() {
        return helpDialog.getDialogPane();
    }

    @Override
    public void initialize(AbstractModel model, TranslationProvider translator) {
        this.helpModel = (HelpEventHandler) model;
        this.applyTranslations(translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        helpDialog.setTitle(translator.translate(helpModel.getTitle()));
        titleLabel.setText(translator.translate(helpModel.getRulesTitle()));
        rulesTitle.setText(translator.translate(helpModel.getRulesHeader()));

        rulesContent.setText("• " + translator.translate(helpModel.getRulesGoal()) + "\n" +
                "• " + translator.translate(helpModel.getRulesTurn()) + "\n" +
                "• " + translator.translate(helpModel.getRulesMatch()) + "\n" +
                "• " + translator.translate(helpModel.getRulesMismatch()));

        featuresTitle.setText(translator.translate(helpModel.getFeatHeader()));
        featuresContent.setText("• " + translator.translate(helpModel.getFeatNew()) + "\n" +
                "• " + translator.translate(helpModel.getFeatSave()) + "\n" +
                "• " + translator.translate(helpModel.getFeatLoad()) + "\n" +
                "• " + translator.translate(helpModel.getFeatPreferences()) + "\n" +
                "• " + translator.translate(helpModel.getFeatLanguage()));

        howTitle.setText(translator.translate(helpModel.getHowHeader()));
        howContent.setText("• " + translator.translate(helpModel.getHowNewGame()) + "\n" +
                "• " + translator.translate(helpModel.getHowFlip()) + "\n" +
                "• " + translator.translate(helpModel.getHowSave()) + "\n" +
                "• " + translator.translate(helpModel.getHowLoad()));
    }

    @Override
    public void update() {
        helpDialog.showAndWait();
    }
}
