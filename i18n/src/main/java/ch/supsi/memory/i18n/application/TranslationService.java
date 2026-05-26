package ch.supsi.memory.i18n.application;

import java.util.Properties;

public interface TranslationService {

    boolean isLanguageTagSupported(String languageTag);

    boolean setLanguageTag(String languageTag);

    String translate(String key);

    void pushTranslations(Properties translations, String forLanguageTag);
}
