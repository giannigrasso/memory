package ch.supsi.memory.frontend.init;

import ch.supsi.memory.frontend.model.TranslationProvider;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsLoader {

    private final String resourcesBaseName;

    private final List<Locale> supportedLocales;

    private final TranslationProvider translator;

    public TranslationsLoader(TranslationProvider translator, String resourcesBaseName, List<Locale> supportedLocales) {
        this.resourcesBaseName = resourcesBaseName;
        this.supportedLocales = supportedLocales;
        this.translator = translator;
    }

    private Properties readBundle(Locale locale) {
        Properties translations = new Properties();
        ResourceBundle bundle = ResourceBundle.getBundle(
                resourcesBaseName,
                locale,
                ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
        // we use Properties because it has a simpler API, and it is serializable,
        // making it easy to port it if our l10n service would communicate differently.
        bundle.keySet().forEach(k -> translations.setProperty(k, bundle.getString(k)));

        return translations;
    }

    public void pushSupportedTranslations() {
        for (Locale supportedLocale : supportedLocales) {
            Properties translations = readBundle(supportedLocale);
            this.translator.pushTranslations(translations, supportedLocale.toLanguageTag());
        }
    }
}
