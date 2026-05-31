package ch.supsi.memory.frontend.tui.view;

import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.UncontrolledView;

public class WelcomeViewTui implements UncontrolledView {

    private static WelcomeViewTui myself;

    private String header;
    private String startupHint;

    protected WelcomeViewTui() {
    }

    public static WelcomeViewTui getInstance() {
        if (myself == null) {
            myself = new WelcomeViewTui();
        }

        return myself;
    }

    @Override
    public void initialize(AbstractModel model, TranslationProvider translator) {
        model = null;

        this.applyTranslations(translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        this.header = translator.translate("label.welcome.header");
        this.startupHint = translator.translate("label.welcome.hint");
    }

    @Override
    public void update() {
        final int PADDING = 4;
        final int headerLen = this.header.length();
        final int hintLen = this.startupHint.length();
        final int maxContent = Math.max(headerLen, hintLen);
        final int width = maxContent + PADDING * 2;
        final int headerLPad = (width - headerLen) / 2;
        final int headerRPad = headerLPad + (width - headerLen) % 2;
        final int hintLPad = (width - hintLen) / 2;
        final int hintRPad = hintLPad + (width - hintLen) % 2;

        System.out.printf("%n╔%s╗%n", "═".repeat(width));
        System.out.printf("║%s%s%s║%n", " ".repeat(headerLPad), this.header, " ".repeat(headerRPad));
        System.out.printf("║%s%s%s║%n", " ".repeat(hintLPad), this.startupHint, " ".repeat(hintRPad));
        System.out.printf("╚%s╝%n%n", "═".repeat(width));
    }
}