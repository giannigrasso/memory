package ch.supsi.memory.frontend.javafx.init;

import ch.supsi.memory.frontend.init.TranslationsLoader;
import ch.supsi.memory.frontend.model.I18nAdapter;

import java.util.List;
import java.util.Locale;

public class TranslationsLoaderFx extends TranslationsLoader {

    private static final List<Locale> SUPPORTED_LOCALES = List.of(
            Locale.of("en", "US"),
            Locale.of("it", "CH")
    );

    public TranslationsLoaderFx() {
        super(I18nAdapter.getInstance(),
                "i18n.labels_fx",
                SUPPORTED_LOCALES);
    }
}
