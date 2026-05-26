package ch.supsi.memory.frontend.init;

import ch.supsi.memory.frontend.model.TranslationProvider;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsLoader {

    public static final String RESOURCES_BASE_NAME = "i18n.labels";

    public static final Locale FALLBACK_LOCALE = Locale.of("en", "US");

    public static final List<Locale> SUPPORTED_LOCALES = List.of(
            FALLBACK_LOCALE,
            Locale.of("it", "CH"));

    private final TranslationProvider translator;

    public TranslationsLoader(TranslationProvider translator) {
        this.translator = translator;
    }

    private Properties readBundle(Locale locale) {
        Properties translations = new Properties();
        ResourceBundle bundle = ResourceBundle.getBundle(
                RESOURCES_BASE_NAME,
                locale,
                ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
        // we use Properties because it has a simpler API, and it is serializable,
        // making it easy to port it if our l10n service would communicate differently.
        bundle.keySet().forEach(k -> translations.setProperty(k, bundle.getString(k)));

        return translations;
    }

    private void pushSupportedTranslations() {
        for (Locale supportedLocale : SUPPORTED_LOCALES) {
            Properties translations = readBundle(supportedLocale);
            this.translator.pushTranslations(translations, supportedLocale.toLanguageTag());
        }
    }

    public void initializeTranslations(String userLanguageTag) {
        pushSupportedTranslations();
        boolean wasSet = this.translator.setLanguageTag(userLanguageTag);
        if (!wasSet) {
            System.err.println("frontend: failed to set locale to " + userLanguageTag);

            // in our case, this will always be true since the frontend fallback
            // and the l10n module default are both 'en-US'.
            // if the frontend default is another locale, then this needs to be checked!
            boolean fallBackSet = this.translator
                    .setLanguageTag(FALLBACK_LOCALE.toLanguageTag());
            if (!fallBackSet) {
                System.err.println("frontend: fallback locale was not pushed (" + FALLBACK_LOCALE.toLanguageTag() + ")");
            } else {
                System.out.println("frontend: l10n fallback to " + FALLBACK_LOCALE.toLanguageTag());
            }
        }
    }
}
