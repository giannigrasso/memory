package ch.supsi.memory.frontend.model;

import java.util.Properties;

public interface TranslationProvider {

    String translate(String key);

    boolean setLanguageTag(String languageTag);

    void pushTranslations(Properties translations, String forLanguageTag);
}
