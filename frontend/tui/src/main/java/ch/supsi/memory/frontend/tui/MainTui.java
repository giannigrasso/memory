package ch.supsi.memory.frontend.tui;

import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.controller.*;
import ch.supsi.memory.frontend.init.InitPhase;
import ch.supsi.memory.frontend.model.*;
import ch.supsi.memory.frontend.tui.cli.*;
import ch.supsi.memory.frontend.tui.controller.QuitDirectorTui;
import ch.supsi.memory.frontend.tui.init.TranslationsLoaderTui;
import ch.supsi.memory.frontend.tui.provider.FilePathProviderTui;
import ch.supsi.memory.frontend.tui.provider.LoadConfirmationProviderTui;
import ch.supsi.memory.frontend.tui.provider.QuitConfirmationProviderTui;
import ch.supsi.memory.frontend.tui.view.*;
import ch.supsi.memory.frontend.view.ControlledView;
import ch.supsi.memory.frontend.view.FilePathProvider;
import ch.supsi.memory.frontend.view.UncontrolledView;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainTui {

    private final AbstractModel gameModel;
    private final AbstractModel userFeedbackModel;
    private final AbstractModel helpModel;
    private final AbstractModel aboutModel;
    private final AbstractModel preferencesModel;

    private final ControlledView menuBarView;
    private final ControlledView toolBarView;
    private final ControlledView gameBoardView;
    private final UncontrolledView userFeedbackView;
    private final UncontrolledView helpView;
    private final UncontrolledView aboutView;
    private final ControlledTuiView preferencesView;
    private final UncontrolledView welcomeView;

    private final GameEventController gameController;
    private final HelpEventController helpController;
    private final AboutEventController aboutController;
    private final PreferencesEventController preferencesController;

    private final TranslationProvider translator;
    private final UserConfirmationProvider confirmQuitModal;
    private final UserConfirmationProvider confirmLoadModal;
    private final FilePathProvider loadPathProvider;
    private final FilePathProvider savePathProvider;

    private final QuitEventController quitController;
    private QuitDirector quitDirector;

    private final CommandRegistry commands;
    private final Scanner input;

    public MainTui() {
        this.input = new Scanner(System.in);

        // MODELS
        this.translator = I18nAdapter.getInstance(); // created in InitPhase::run
        this.gameModel = GameModel.getInstance();
        this.userFeedbackModel = UserFeedbackModel.getInstance();
        this.helpModel = HelpModel.getInstance();
        this.aboutModel = AboutModel.getInstance();
        this.preferencesModel = PreferencesModel.getInstance();

        // VIEWS
        this.menuBarView = MenuBarViewTui.getInstance();
        this.toolBarView = null;
        this.gameBoardView = GameBoardViewTui.getInstance();
        this.userFeedbackView = UserFeedbackViewTui.getInstance();
        this.helpView = HelpViewTui.getInstance();
        this.aboutView = AboutViewTui.getInstance();
        this.preferencesView = PreferencesViewTui.getInstance();
        this.welcomeView = WelcomeViewTui.getInstance();

        // CONTROLLERS
        this.preferencesController = PreferencesController.getInstance(); // created in InitPhase::run
        this.gameController = GameController.getInstance();
        this.quitController = QuitController.getInstance();
        this.helpController = HelpController.getInstance();
        this.aboutController = AboutController.getInstance();

        // MODALS
        this.confirmQuitModal = new QuitConfirmationProviderTui(this.input, this.translator);
        this.confirmLoadModal = new LoadConfirmationProviderTui(this.input, this.translator);
        this.loadPathProvider = new FilePathProviderTui(new String[0]);
        this.savePathProvider = new FilePathProviderTui(new String[0]);

        // COMMANDS
        this.commands = new CommandRegistry(
                this.gameController,
                this.preferencesController,
                this.quitController,
                this.aboutController,
                this.helpController);

        // SCAFFOLDING of M-V-C
        this.menuBarView.initialize(commands, this.gameModel, this.translator);
//        this.toolBarView.initialize(commands, this.gameModel, this.translator);
        this.gameBoardView.initialize(commands, this.gameModel, this.translator);
        this.userFeedbackView.initialize(this.userFeedbackModel, this.translator);
        this.helpView.initialize(this.helpModel, this.translator);
        this.aboutView.initialize(this.aboutModel, this.translator);
        this.preferencesView.initialize(commands, this.preferencesModel, this.translator);
        this.preferencesView.setInput(this.input);
        this.welcomeView.initialize(null, this.translator);

        GameController.getInstance().init(
                List.of(menuBarView, gameBoardView),
                userFeedbackView,
                confirmLoadModal,
                loadPathProvider,
                savePathProvider);
        HelpController.getInstance().initialize(List.of(this.helpView));
        AboutController.getInstance().initialize(List.of(this.aboutView));
        PreferencesController.getInstance().initialize(List.of(this.preferencesView));
    }

    public void start() {
        this.quitDirector = new QuitDirectorTui(
                (QuitEventHandler) this.gameModel,
                this.confirmQuitModal);
        QuitController.getInstance().registerQuitDirector(this.quitDirector);

        // STARTUP FEEDBACK
//        final FeedbackCommand feedbackCmd = commands.get(FeedbackCommand.class);
//        String text = this.translator.translate("label.feedback.press_new_to_start");
//        text = text.replace("%new", this.translator.translate("label.new"));
//        feedbackCmd.setText(text);
//        feedbackCmd.execute();

        final Map<String, CliCommandFactory> factories = new HashMap<>();
        factories.put("about", new AboutCliCommandFactory(this.aboutController));
        factories.put("flip", new FlipCliCommandFactory(this.gameController));
        factories.put("help", new HelpCliCommandFactory(this.helpController));
        factories.put("load", new LoadGameCliCommandFactory(this.gameController, (FilePathProviderTui) this.loadPathProvider));
        factories.put("new", new NewGameCliCommandFactory(this.gameController, this.preferencesController));
        factories.put("quit", new QuitCliCommandFactory(this.quitController));
        factories.put("save", new SaveCliCommandFactory(this.gameController));
        factories.put("saveas", new SaveAsCliCommandFactory(this.gameController, (FilePathProviderTui) this.savePathProvider));
        factories.put("prefs", new ShowPreferencesCliCommandFactory(this.preferencesController));

        this.welcomeView.update();
        this.menuBarView.update();

        final CliCommandHandler cmdHandler = new CliCommandHandler(this.input, factories);
        while (!((QuitDirectorTui) this.quitDirector).mustQuit()) {
            try {
                cmdHandler.handleLine();
            } catch (CliCommandFactory.CliCommandException e) {
                System.out.println(e.getLocalizedMessage(this.translator));
            }
        }

        this.input.close();
    }

    public static void main(String[] args) {
        InitPhase.run(List.of(new TranslationsLoaderTui()));
        new MainTui().start();
    }
}
