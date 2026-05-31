package ch.supsi.memory.frontend.tui.view;


import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.command.EditPreferencesCommand;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.PreferencesEventHandler;
import ch.supsi.memory.frontend.model.TranslationProvider;

import java.util.Scanner;

public class PreferencesViewTui implements ControlledTuiView {

    private static PreferencesViewTui myself;

    private PreferencesEventHandler prefsModel;
    private CommandRegistry commands;
    private Scanner input;

    private String title;
    private String langLabel;
    private String batchSizeLabel;

    protected PreferencesViewTui() {
    }

    public static PreferencesViewTui getInstance() {
        if (myself == null) {
            myself = new PreferencesViewTui();
        }

        return myself;
    }

    @Override
    public void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator) {
        this.prefsModel = (PreferencesEventHandler) model;
        this.commands = commands;

        this.applyTranslations(translator);
    }

    @Override
    public void setInput(Scanner input) {
        this.input = input;
    }

    private void applyTranslations(TranslationProvider translator) {
        title = translator.translate("label.editprefs.title");

        langLabel = translator.translate("label.editprefs.label.language");
        batchSizeLabel = translator.translate("label.editprefs.label.batch_size");
    }

    @Override
    public void update() {
        if (this.input == null) {
            throw new IllegalStateException("input provider is null");
        }

        if (!this.prefsModel.isShowing()) {
            return;
        }

        System.out.println();
        System.out.println(title);
        System.out.println(langLabel + " | " + prefsModel.getLocale());
        System.out.println("1) it-CH");
        System.out.println("2) en-US");
        System.out.print("> ");

        String localeInput = input.nextLine().trim();
        String locale = switch (localeInput) {
            case "1" -> "it-CH";
            case "2" -> "en-US";
            default -> prefsModel.getLocale();
        };

        System.out.println(batchSizeLabel + " | " + prefsModel.getBatchSize());
        System.out.println("2) 2");
        System.out.println("3) 3");
        System.out.println("4) 4");
        System.out.println("6) 6");
        System.out.println("8) 8");
        System.out.print("> ");

        String batchInput = input.nextLine().trim();

        int batchSize = switch (batchInput) {
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "6" -> 6;
            case "8" -> 8;
            default -> prefsModel.getBatchSize();
        };

        EditPreferencesCommand cmd = commands.get(EditPreferencesCommand.class);
        cmd.setLocale(locale);
        cmd.setBatchSize(batchSize);
        cmd.execute();
    }
}
