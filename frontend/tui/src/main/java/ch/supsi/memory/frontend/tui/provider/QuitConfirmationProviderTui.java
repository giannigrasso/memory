package ch.supsi.memory.frontend.tui.provider;

import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.UserConfirmationProvider;

import java.util.Scanner;

public class QuitConfirmationProviderTui implements UserConfirmationProvider {

    private final Scanner input;
    private final TranslationProvider translator;

    private String title;
    private String text;
    private String confirmation;

    public QuitConfirmationProviderTui(Scanner input, TranslationProvider translator) {
        this.input = input;
        this.translator = translator;

        this.applyTranslations(translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        this.title = this.translator.translate("label.confirm_quit.title");
        final String translatedText = this.translator.translate("label.confirm_quit.text");
        final String yes = this.translator.translate("label.yes")
                .toLowerCase()
                .substring(0, 1);
        this.confirmation = yes;
        final String no = this.translator.translate("label.no")
                .toUpperCase()
                .substring(0, 1);
        this.text = String.format("%s (%s/%s) ", translatedText, yes, no);
    }

    @Override
    public boolean confirm() {
        System.out.println(this.title);
        System.out.print(this.text);

        final String answer = input.nextLine().trim().toLowerCase();

        return answer.equals(this.confirmation);
    }
}
