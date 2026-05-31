package ch.supsi.memory.frontend.tui.init;

import ch.supsi.memory.frontend.init.TranslationsLoader;
import ch.supsi.memory.frontend.model.I18nAdapter;

import java.util.List;
import java.util.Locale;

public class TranslationsLoaderTui extends TranslationsLoader {

    private static final List<Locale> SUPPORTED_LOCALES = List.of(
            Locale.of("en", "US"),
            Locale.of("it", "CH")
    );

    public TranslationsLoaderTui() {
        super(I18nAdapter.getInstance(),
                "i18n.labels_tui",
                SUPPORTED_LOCALES);
    }
}
