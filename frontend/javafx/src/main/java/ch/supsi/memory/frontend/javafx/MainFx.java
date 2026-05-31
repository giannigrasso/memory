package ch.supsi.memory.frontend.javafx;

import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.controller.*;
import ch.supsi.memory.frontend.init.InitPhase;
import ch.supsi.memory.frontend.javafx.controller.QuitDirectorJfx;
import ch.supsi.memory.frontend.javafx.init.TranslationsLoaderFx;
import ch.supsi.memory.frontend.javafx.view.*;
import ch.supsi.memory.frontend.javafx.view.modal.ConfirmLoadModal;
import ch.supsi.memory.frontend.javafx.view.modal.ConfirmQuitModal;
import ch.supsi.memory.frontend.javafx.view.modal.LoadGameModal;
import ch.supsi.memory.frontend.javafx.view.modal.SaveAsModal;
import ch.supsi.memory.frontend.model.*;
import ch.supsi.memory.frontend.view.FilePathProvider;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MainFx extends Application {

    public static final String APP_TITLE = "memory";

    private final AbstractModel gameModel;
    private final AbstractModel userFeedbackModel;
    private final AbstractModel helpModel;
    private final AbstractModel aboutModel;
    private final AbstractModel preferencesModel;

    private final ControlledFxView menuBarView;
    private final ControlledFxView toolBarView;
    private final ControlledFxView gameBoardView;
    private final UncontrolledFxView userFeedbackView;
    private final UncontrolledFxView helpView;
    private final UncontrolledFxView aboutView;
    private final ControlledFxView preferencesView;

    private final GameEventController gameController;
    private final HelpEventController helpController;
    private final AboutEventController aboutController;
    private final PreferencesEventController preferencesController;

    private final TranslationProvider translator;
    private final UserConfirmationProvider confirmQuitModal;
    private final UserConfirmationProvider confirmLoadModal;
    private final FilePathProvider loadGameModal;
    private final FilePathProvider saveGameModal;

    private final QuitEventController quitController;
    private QuitDirector quitDirector;

    private final CommandRegistry commands;

    public MainFx() {
        // MODELS
        this.translator = I18nAdapter.getInstance(); // created in InitPhase::run
        this.gameModel = GameModel.getInstance();
        this.userFeedbackModel = UserFeedbackModel.getInstance();
        this.helpModel = HelpModel.getInstance();
        this.aboutModel = AboutModel.getInstance();
        this.preferencesModel = PreferencesModel.getInstance();

        // VIEWS
        this.menuBarView = MenuBarViewFxml.getInstance();
        this.toolBarView = ToolBarViewFxml.getInstance();
        this.gameBoardView = GameBoardViewFxml.getInstance();
        this.userFeedbackView = UserFeedbackViewFxml.getInstance();
        this.helpView = HelpView.getInstance();
        this.aboutView = AboutView.getInstance();
        this.preferencesView = PreferencesView.getInstance();

        // CONTROLLERS
        this.preferencesController = PreferencesController.getInstance(); // created in InitPhase::run
        this.gameController = GameController.getInstance();
        this.quitController = QuitController.getInstance();
        this.helpController = HelpController.getInstance();
        this.aboutController = AboutController.getInstance();

        // MODALS
        this.confirmQuitModal = new ConfirmQuitModal(this.translator);
        this.confirmLoadModal = new ConfirmLoadModal(this.translator);
        this.loadGameModal = new LoadGameModal(this.translator);
        this.saveGameModal = new SaveAsModal(this.translator);

        // COMMANDS
        this.commands = new CommandRegistry(
                this.gameController,
                this.preferencesController,
                this.quitController,
                this.aboutController,
                this.helpController);

        // SCAFFOLDING of M-V-C
        this.menuBarView.initialize(commands, this.gameModel, this.translator);
        this.toolBarView.initialize(commands, this.gameModel, this.translator);
        this.gameBoardView.initialize(commands, this.gameModel, this.translator);
        this.userFeedbackView.initialize(this.userFeedbackModel, this.translator);
        this.helpView.initialize(this.helpModel, this.translator);
        this.aboutView.initialize(this.aboutModel, this.translator);
        this.preferencesView.initialize(commands, this.preferencesModel, this.translator);

        GameController.getInstance().init(
                List.of(this.menuBarView, this.toolBarView, this.gameBoardView),
                userFeedbackView,
                confirmLoadModal,
                loadGameModal,
                saveGameModal);
        HelpController.getInstance().initialize(List.of(this.helpView));
        AboutController.getInstance().initialize(List.of(this.aboutView));
        PreferencesController.getInstance().initialize(List.of(this.preferencesView));
    }

    @Override
    public void start(Stage primaryStage) {
        this.quitDirector = new QuitDirectorJfx(
                (QuitEventHandler) this.gameModel,
                this.confirmQuitModal,
                primaryStage);
        QuitController.getInstance().registerQuitDirector(this.quitDirector);

        primaryStage.setOnCloseRequest(
                windowEvent -> {
                    windowEvent.consume();

                    this.quitController.quit();
                }
        );

        // SCAFFOLDING OF MAIN PANE
        final BorderPane mainBorderPane = new BorderPane();
        final VBox topVBox = new VBox();
        topVBox.getChildren().add(this.menuBarView.getNode());
        topVBox.getChildren().add(this.toolBarView.getNode());
        mainBorderPane.setTop(topVBox);
        mainBorderPane.setCenter(this.gameBoardView.getNode());
        mainBorderPane.setBottom(this.userFeedbackView.getNode());

        // SCENE
        final Scene scene = new Scene(mainBorderPane);

        // PRIMARY STAGE
        primaryStage.setTitle(MainFx.APP_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.show();
    }

    public static void main(String[] args) {
        InitPhase.run(List.of(new TranslationsLoaderFx()));
        launch(args);
    }
}
