package ch.supsi.memory.frontend.view;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.controller.GameEventController;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class MenuBarViewFxml implements ControlledFxView {

    private static MenuBarViewFxml myself;

    private GameEventController gameEventController;
    private GameModel gameModel;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;
    @FXML
    private Menu editMenu;
    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private MenuItem preferencesMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem helpMenuItem;

    private MenuBarViewFxml() {}

    public static MenuBarViewFxml getInstance() {
        if (myself == null) {
            myself = new MenuBarViewFxml();

            try {
                URL fxmlUrl = MenuBarViewFxml.class.getResource("/menubar.fxml");
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
        this.newMenuItem.setOnAction(event -> commands.get("newGame").execute());

        // save
        this.saveMenuItem.setOnAction(event -> commands.get("saveGame").execute());

        // add event handlers for all necessary menu items
        // ...
    }

    @Override
    public Node getNode() {
        return this.menuBar;
    }

    @Override
    public void update() {
        // get your data from the model, if needed
        // then update this view here
        System.out.println(this.getClass().getSimpleName() + " updated on..." + System.currentTimeMillis());
    }

}
