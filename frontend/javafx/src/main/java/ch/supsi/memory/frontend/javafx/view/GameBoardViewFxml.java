package ch.supsi.memory.frontend.javafx.view;

import ch.supsi.memory.backend.model.GameObject;
import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.command.FeedbackCommand;
import ch.supsi.memory.frontend.command.FlipCommand;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;

public class GameBoardViewFxml implements ControlledFxView {

    private static GameBoardViewFxml myself;

    private TranslationProvider translator;

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

    @FXML
    private Button cell70;

    @FXML
    private Button cell71;

    @FXML
    private Button cell72;

    @FXML
    private Button cell73;

    @FXML
    private Button cell74;

    @FXML
    private Button cell75;

    private Button[][] gridButtons;

    private GameBoardViewFxml() {
    }

    private void setupButtonsMatrix() {
        this.gridButtons = new Button[][]{
                {cell00, cell01, cell02, cell03, cell04, cell05},
                {cell10, cell11, cell12, cell13, cell14, cell15},
                {cell20, cell21, cell22, cell23, cell24, cell25},
                {cell30, cell31, cell32, cell33, cell34, cell35},
                {cell40, cell41, cell42, cell43, cell44, cell45},
                {cell50, cell51, cell52, cell53, cell54, cell55},
                {cell60, cell61, cell62, cell63, cell64, cell65},
                {cell70, cell71, cell72, cell73, cell74, cell75},
        };
    }

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

            // NOTE: this has to be called here since FXML has now populated
            // the cellXX fields. Not calling it or calling it before results
            // in a NullPointerException since the matrix will be filled with nulls.
            myself.setupButtonsMatrix();
        }

        return myself;
    }

    @Override
    public void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator) {
        this.translator = translator;
        this.gameModel = (GameModel) model;

        this.createBehaviour(commands);
    }

    private void createBehaviour(CommandRegistry commands) {
        FlipCommand flipCmd = commands.get(FlipCommand.class);
        FeedbackCommand feedbackCmd = commands.get(FeedbackCommand.class);

        for (int row = 0; row < this.gridButtons.length; row++) {
            for (int column = 0; column < this.gridButtons[row].length; column++) {
                final int x = column;
                final int y = row;
                final Button btn = this.gridButtons[row][column];

                btn.setOnAction(event -> {
                    flipCmd.setCell(x, y);
                    flipCmd.execute();

                    int flipped = this.gameModel.getCurrentTurnFlippedCount();
                    final String msg = String.format(
                            "%s %d/%d%n",
                            this.translator.translate("label.flipped"),
                            flipped,
                            this.gameModel.getBatchSize());
                    feedbackCmd.setText(msg);
                    feedbackCmd.execute();
                });
            }
        }
    }

    @Override
    public Node getNode() {
        return this.containerPane;
    }

    @Override
    public void update() {
        for (int row = 0; row < this.gridButtons.length; row++) {
            for (int column = 0; column < this.gridButtons[row].length; column++) {
                final GameObject card = this.gameModel.getAt(new int[]{column, row});
                final String btnText = card.isFlipped() ? "" + card.getSymbol() : "?";
                final Button btn = this.gridButtons[row][column];

                // FIX: since atm the only moment where the grid is disabled is
                // at the start of the app, it's useless to keep setting this
                // because it'll always be true.
                btn.setDisable(this.gameModel.isNoGameLoaded());
                btn.setText(btnText);
            }
        }
    }
}
