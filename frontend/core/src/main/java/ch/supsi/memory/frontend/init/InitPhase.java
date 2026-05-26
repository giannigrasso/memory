package ch.supsi.memory.frontend.init;

import ch.supsi.memory.backend.business.BadUserPreferencesException;
import ch.supsi.memory.backend.business.validate.UserPreferencesRule;
import ch.supsi.memory.frontend.controller.PreferencesController;
import ch.supsi.memory.frontend.controller.PreferencesEventController;
import ch.supsi.memory.frontend.model.I18nAdapter;
import ch.supsi.memory.frontend.model.TranslationProvider;

public class InitPhase {

    public static void run() {
        PreferencesEventController preferencesEventController = requireUserPreferences();
        TranslationProvider translator = requireTranslationProvider();

        initializeTranslations(translator, preferencesEventController);
    }

    private static PreferencesEventController requireUserPreferences() {
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

    // NOTE: future guard. as of now, our l10n service has no init errors.
    private static TranslationProvider requireTranslationProvider() {
        return I18nAdapter.getInstance();
    }

    private static void initializeTranslations(TranslationProvider translator, PreferencesEventController preferencesEventController) {
        TranslationsLoader translationsLoader = new TranslationsLoader(translator);
        String userLanguageTag = preferencesEventController.getLocale();
        translationsLoader.initializeTranslations(userLanguageTag);
    }
}
