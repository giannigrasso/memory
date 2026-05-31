package ch.supsi.memory.frontend.model;

import ch.supsi.memory.i18n.application.TranslationController;

import java.util.Locale;
import java.util.Properties;

public class I18nAdapter implements TranslationProvider {

    public static final Locale FALLBACK_LOCALE = Locale.of("en", "US");

    private static I18nAdapter myself;

    private final TranslationController backend;

    protected I18nAdapter(TranslationController backend) {
        this.backend = backend;
    }

    public static I18nAdapter getInstance() {
        if (myself == null) {
            myself = new I18nAdapter(TranslationController.getInstance());
        }

        return myself;
    }

    @Override
    public String translate(String key) {
        return this.backend.translate(key);
    }

    @Override
    public boolean setLanguageTag(String languageTag) {
        return this.backend.setLanguageTag(languageTag);
    }

    @Override
    public void pushTranslations(Properties translations, String forLanguageTag) {
        this.backend.pushTranslations(translations, forLanguageTag);
    }
}
