package ch.supsi.memory.frontend.javafx.view;

import ch.supsi.memory.frontend.command.*;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;

public class MenuBarViewFxml implements ControlledFxView {

    private static MenuBarViewFxml myself;

    private TranslationProvider translator;
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

    private MenuBarViewFxml() {
    }

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
    public void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator) {
        this.translator = translator;
        this.gameModel = (GameModel) model;

        this.createBehaviour(commands);
        this.applyTranslations(this.translator);
    }

    private void createBehaviour(CommandRegistry commands) {
        // new
        this.newMenuItem.setOnAction(event -> {
            NewGameCommand newGameCmd = commands.get(NewGameCommand.class);
            newGameCmd.execute();
        });

        // save
        this.saveMenuItem.setOnAction(event -> {
            commands.get(SaveCommand.class).execute();
        });

        // saveAs
        this.saveAsMenuItem.setOnAction(event -> {
            SaveAsCommand saveAsCmd = commands.get(SaveAsCommand.class);
            saveAsCmd.execute();
        });

        this.quitMenuItem.setOnAction(event -> commands.get(QuitCommand.class).execute());

        this.openMenuItem.setOnAction(event -> {
            LoadGameCommand loadCmd = commands.get(LoadGameCommand.class);
            loadCmd.execute();
        });

        this.aboutMenuItem.setOnAction(event -> commands.get(AboutCommand.class).execute());

        this.helpMenuItem.setOnAction(event -> commands.get(HelpCommand.class).execute());

        this.preferencesMenuItem.setOnAction(event -> {
            commands.get(ShowPreferencesCommand.class).execute();
        });
    }

    private void applyTranslations(TranslationProvider translator) {
        this.fileMenu.setText(translator.translate("label.file"));
        this.newMenuItem.setText(translator.translate("label.new"));
        this.openMenuItem.setText(translator.translate("label.open"));
        this.saveMenuItem.setText(translator.translate("label.save"));
        this.saveAsMenuItem.setText(translator.translate("label.saveas"));
        this.quitMenuItem.setText(translator.translate("label.quit"));

        this.editMenu.setText(translator.translate("label.edit"));
        this.preferencesMenuItem.setText(translator.translate("label.prefs"));

        this.helpMenu.setText(translator.translate("label.help"));
        this.aboutMenuItem.setText(translator.translate("label.about"));
        this.helpMenuItem.setText(translator.translate("label.help"));
    }

    @Override
    public Node getNode() {
        return this.menuBar;
    }

    @Override
    public void update() {
        boolean showSaveBtn = this.gameModel.isDirty() && this.gameModel.hasFilePath();
        this.saveMenuItem.setDisable(!showSaveBtn);
        boolean showSaveAsBtn = this.gameModel.isDirty();
        this.saveAsMenuItem.setDisable(!showSaveAsBtn);
    }
}
