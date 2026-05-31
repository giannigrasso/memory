package ch.supsi.memory.frontend.tui.view;


import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.ControlledView;

public class MenuBarViewTui implements ControlledView {

    private static MenuBarViewTui myself;

    private TranslationProvider translator;
    private GameModel gameModel;
    private String nuovo;
    private String load;
    private String save;
    private String saveas;
    private String flip;
    private String prefs;
    private String help;
    private String about;
    private String quit;


    private MenuBarViewTui() {
    }

    public static MenuBarViewTui getInstance() {
        if (myself == null) {
            myself = new MenuBarViewTui();

        }

        return myself;
    }

    @Override
    public void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator) {
        this.translator = translator;
        this.gameModel = (GameModel) model;

        this.applyTranslations(this.translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        nuovo = "new";
        load = "load";
        save = "save";
        saveas = "saves";
        flip = "flip";
        prefs = "prefs";
        help = "help";
        about = "about";
        quit = "quit";
    }

    @Override
    public void update() {
        displayCommand();
    }

    private void displayCommand() {
        System.out.println();
        System.out.println(
                "[" + nuovo + "]   " +
                        "[" + load + "]   " +
                        "[" + save + "]   " +
                        "[" + saveas + "]   " +
                        "[" + flip + "]   " +
                        "[" + prefs + "]   " +
                        "[" + help + "]   " +
                        "[" + about + "]   " +
                        "[" + quit + "]\n"
        );
    }
}
