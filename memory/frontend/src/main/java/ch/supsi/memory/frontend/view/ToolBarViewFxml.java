package ch.supsi.memory.frontend.view;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.controller.GameEventController;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class ToolBarViewFxml implements ControlledFxView {

    private static ToolBarViewFxml myself;

    private GameEventController gameEventController;
    private GameModel gameModel;

    @FXML
    private ToolBar toolBar;

    @FXML
    private Button newButton;
    @FXML
    private Button saveButton;

    private ToolBarViewFxml() {}

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
    public void initialize(HashMap<String, Command> commands, AbstractModel model) {
        this.createBehaviour(commands);
        this.gameModel = (GameModel) model;
    }

    private void createBehaviour(HashMap<String, Command> commands) {
        // new
        this.newButton.setOnAction(event -> commands.get("newGame").execute());

        // save
        this.saveButton.setOnAction(event -> commands.get("saveGame").execute());

        // add event handlers for all necessary menu items
        // ...
    }

    @Override
    public Node getNode() {
        return this.toolBar;
    }

    @Override
    public void update() {
        // get your data from the model, if needed
        // then update this view here
        System.out.println(this.getClass().getSimpleName() + " updated on..." + System.currentTimeMillis());
    }

}
