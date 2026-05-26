package ch.supsi.memory.i18n.business;

import java.util.Properties;
import java.util.Set;

public interface TranslationDao {

    Set<String> getSupportedLanguageTags();

    void pushTranslations(Properties translations, String languageTag);

    Properties getTranslations(String languageTag);
}
