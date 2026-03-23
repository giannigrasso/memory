package ch.supsi.memory.frontend;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.command.FlipCommand;
import ch.supsi.memory.frontend.command.SaveCommand;
import ch.supsi.memory.frontend.controller.GameController;
import ch.supsi.memory.frontend.controller.GameEventController;
import ch.supsi.memory.frontend.controller.PlayerEventController;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class MainFx extends Application {

    public static final String APP_TITLE = "memory";

    private final AbstractModel gameModel;
    private final ControlledFxView menuBarView;
    private final ControlledFxView toolBarView;
    private final ControlledFxView gameBoardView;
    private final UncontrolledFxView userFeedbackView;
    private final GameEventController gameEventController;
    private final PlayerEventController playerEventController;

    public MainFx() {
        // GAME MODEL
        this.gameModel = GameModel.getInstance();

        // VIEWS
        this.menuBarView = MenuBarViewFxml.getInstance();
        this.toolBarView = ToolBarViewFxml.getInstance();
        this.gameBoardView = GameBoardViewFxml.getInstance();
        this.userFeedbackView = UserFeedbackViewFxml.getInstance();

        // CONTROLLERS
        this.gameEventController = GameController.getInstance();
        this.playerEventController = GameController.getInstance();

        // COMMANDS
        HashMap<String, Command> commands = new HashMap<>();

        Command saveCommand = new SaveCommand(this.gameEventController);
        commands.put("saveGame", saveCommand);

        Command flipCommand = new FlipCommand(this.playerEventController);
        commands.put("flipCell", flipCommand);

        // SCAFFOLDING of M-V-C
        this.menuBarView.initialize(commands, this.gameModel);
        this.gameBoardView.initialize(commands, this.gameModel);
        this.userFeedbackView.initialize(this.gameModel);
        GameController.getInstance().initialize(List.of(this.menuBarView, this.gameBoardView, this.userFeedbackView));
    }

    @Override
    public void start(Stage primaryStage) {
        // handle the main window close request
        // in real life, this event should not be dealt with here!
        // it should actually be delegated to a suitable ExitController!
        primaryStage.setOnCloseRequest(
                windowEvent -> {
                    // consume the window event (the main window would be closed otherwise no matter what)
                    windowEvent.consume();

                    // quit the app
                    // remove this hard close
                    // delegate the work to a suitable controller
                    primaryStage.close();
                }
        );

        // SCAFFOLDING OF MAIN PANE
        BorderPane mainBorderPane = new BorderPane();
        VBox topVBox = new VBox();
        topVBox.getChildren().add(this.menuBarView.getNode());
        topVBox.getChildren().add(this.toolBarView.getNode());
        mainBorderPane.setTop(topVBox);
        mainBorderPane.setCenter(this.gameBoardView.getNode());
        mainBorderPane.setBottom(this.userFeedbackView.getNode());

        // SCENE
        Scene scene = new Scene(mainBorderPane);

        // PRIMARY STAGE
        primaryStage.setTitle(MainFx.APP_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
