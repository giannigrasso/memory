package ch.supsi.memory.frontend.javafx.view;

import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.command.NewGameCommand;
import ch.supsi.memory.frontend.command.SaveCommand;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

import java.io.IOException;
import java.net.URL;

public class ToolBarViewFxml implements ControlledFxView {

    private static ToolBarViewFxml myself;

    private TranslationProvider translator;

    private GameModel gameModel;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button newButton;
    @FXML
    private Button saveButton;

    private ToolBarViewFxml() {
    }

    public static ToolBarViewFxml getInstance() {
        if (myself == null) {
            myself = new ToolBarViewFxml();

            try {
                URL fxmlUrl = ToolBarViewFxml.class.getResource("/toolbar.fxml");
                if (fxmlUrl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                    fxmlLoader.setController(myself);
                    fxmlLoader.load();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return myself;
    }

    @Override
    public void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator) {
        this.translator = translator;
        this.gameModel = (GameModel) model;

        this.createBehaviour(commands);
        this.applyTranslations(this.translator);
    }

    private void createBehaviour(CommandRegistry commands) {
        // new
        this.newButton.setOnAction(event -> {
            NewGameCommand newGameCmd = commands.get(NewGameCommand.class);
            newGameCmd.execute();
        });

        // save
        this.saveButton.setOnAction(event -> {
            SaveCommand saveCmd = commands.get(SaveCommand.class);
            saveCmd.execute();
        });
    }

    private void applyTranslations(TranslationProvider translator) {
        this.newButton.setText(translator.translate("label.new"));
        this.saveButton.setText(translator.translate("label.save"));
    }

    @Override
    public Node getNode() {
        return this.toolBar;
    }

    @Override
    public void update() {
        boolean showSaveBtn = this.gameModel.isDirty() && this.gameModel.hasFilePath();
        this.saveButton.setDisable(!showSaveBtn);
    }
}
