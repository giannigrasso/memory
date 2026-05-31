package ch.supsi.memory.frontend.init;

import ch.supsi.memory.backend.application.PreferencesController;
import ch.supsi.memory.backend.business.BadUserPreferencesException;
import ch.supsi.memory.backend.business.validate.UserPreferencesRule;
import ch.supsi.memory.frontend.model.I18nAdapter;
import ch.supsi.memory.frontend.model.TranslationProvider;

import java.util.List;
import java.util.Locale;

public class InitPhase {

    public static void run(List<TranslationsLoader> customLoaders) {
        final PreferencesController preferencesEventController = requireUserPreferences();
        final TranslationProvider translator = requireTranslationProvider();
        final String locale = preferencesEventController.getLocale();

        pushLoadedTranslations(translator, customLoaders);
        setUserLocaleOrFallback(translator, locale, I18nAdapter.FALLBACK_LOCALE);
    }

    private static PreferencesController requireUserPreferences() {
        try {
            return PreferencesController.getInstance();
        } catch (BadUserPreferencesException e) {
            System.err.println(e.getMessage());
            System.err.println("The user preferences file is invalid or corrupted.");
            System.err.println("The file must contain the following:");
            System.err.println(UserPreferencesRule.ERROR);
            System.err.println("Please, fix the file or remove it to reset defaults.");
            System.exit(1);
            return null;
        }
    }

    // NOTE: future guard. as of now, our i18n service has no init errors.
    private static TranslationProvider requireTranslationProvider() {
        return I18nAdapter.getInstance();
    }

    private static void pushLoadedTranslations(TranslationProvider translator, List<TranslationsLoader> customLoaders) {
        // core loader
        final List<Locale> supportedLocales = List.of(
                Locale.of("en", "US"),
                Locale.of("it", "CH"));
        final TranslationsLoader translationsLoader = new TranslationsLoader(
                translator,
                "i18n.labels",
                supportedLocales);
        translationsLoader.pushSupportedTranslations();

        // custom loaders
        customLoaders.forEach(TranslationsLoader::pushSupportedTranslations);
    }

    private static void setUserLocaleOrFallback(TranslationProvider translator, String locale, Locale fallback) {
        // set fallback
        final boolean wasSet = translator.setLanguageTag(locale);
        if (!wasSet) {
            // TODO: would be better to let the user know!
            System.err.println("frontend: failed to set locale to " + locale);

            // in our case, this will always be true since the frontend fallback
            // and the l10n module default are both 'en-US'.
            // if the frontend default is another locale, then this needs to be checked!
            final String fallbackLocale = fallback.toLanguageTag();
            boolean fallBackSet = translator.setLanguageTag(fallbackLocale);
            if (!fallBackSet) {
                System.err.println("frontend: fallback locale was not pushed (" + fallbackLocale + ")");
            } else {
                System.out.println("frontend: i18n fallback to " + fallbackLocale);
            }
        }
    }
}
