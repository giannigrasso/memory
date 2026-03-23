package ch.supsi.memory.frontend.view;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.FlipCommand;
import ch.supsi.memory.frontend.controller.PlayerEventController;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GameBoardViewFxml implements ControlledFxView {

    private static GameBoardViewFxml myself;

    private PlayerEventController playerEventController;

    private GameModel gameModel;

    @FXML
    private GridPane containerPane;

    @FXML
    private Button cell00;

    @FXML
    private Button cell01;

    @FXML
    private Button cell02;

    @FXML
    private Button cell03;

    @FXML
    private Button cell04;

    @FXML
    private Button cell05;

    @FXML
    private Button cell10;

    @FXML
    private Button cell11;

    @FXML
    private Button cell12;

    @FXML
    private Button cell13;

    @FXML
    private Button cell14;

    @FXML
    private Button cell15;

    @FXML
    private Button cell20;

    @FXML
    private Button cell21;

    @FXML
    private Button cell22;

    @FXML
    private Button cell23;

    @FXML
    private Button cell24;

    @FXML
    private Button cell25;

    @FXML
    private Button cell30;

    @FXML
    private Button cell31;

    @FXML
    private Button cell32;

    @FXML
    private Button cell33;

    @FXML
    private Button cell34;

    @FXML
    private Button cell35;

    @FXML
    private Button cell40;

    @FXML
    private Button cell41;

    @FXML
    private Button cell42;

    @FXML
    private Button cell43;

    @FXML
    private Button cell44;

    @FXML
    private Button cell45;

    @FXML
    private Button cell50;

    @FXML
    private Button cell51;

    @FXML
    private Button cell52;

    @FXML
    private Button cell53;

    @FXML
    private Button cell54;

    @FXML
    private Button cell55;

    @FXML
    private Button cell60;

    @FXML
    private Button cell61;

    @FXML
    private Button cell62;

    @FXML
    private Button cell63;

    @FXML
    private Button cell64;

    @FXML
    private Button cell65;

    private GameBoardViewFxml() {}

    public static GameBoardViewFxml getInstance() {
        if (myself == null) {
            myself = new GameBoardViewFxml();

            try {
                URL fxmlUrl = GameBoardViewFxml.class.getResource("/gameboard.fxml");
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

        // useful in case we need to fetch extra info from our game model
        this.gameModel = (GameModel) model;
    }

    private void createBehaviour(HashMap<String, Command> commands) {
        FlipCommand command = (FlipCommand) commands.get("flipCell");

        // cell00
        this.cell00.setOnAction(event -> { command.setCell(0, 0); command.execute(); });

        // cell01
        this.cell01.setOnAction(event -> { command.setCell(0, 1); command.execute(); });

        // add event handlers for all necessary buttons
        // ...
    }

    @Override
    public Node getNode() {
        return this.containerPane;
    }

    @Override
    public void update() {
        // get your data from the model, if needed
        // then update this view here
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(this.getClass().getSimpleName() + " updated on..." + dateFormat.format(date));
    }

}
