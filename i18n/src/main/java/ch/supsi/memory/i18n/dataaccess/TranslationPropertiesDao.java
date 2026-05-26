package ch.supsi.memory.i18n.dataaccess;

import ch.supsi.memory.i18n.business.TranslationDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TranslationPropertiesDao implements TranslationDao {

    private static TranslationPropertiesDao myself;

    private final Map<String, Properties> translationResources;

    protected TranslationPropertiesDao() {
        this.translationResources = new HashMap<>();
    }

    public static TranslationDao getInstance() {
        if (myself == null) {
            myself = new TranslationPropertiesDao();
        }

        return myself;
    }

    @Override
    public Set<String> getSupportedLanguageTags() {
        return this.translationResources.keySet();
    }

    @Override
    public void pushTranslations(Properties translations, String languageTag) {
        this.translationResources.merge(languageTag, translations, (existing, provided) -> {
            existing.putAll(provided);
            return existing;
        });
    }

    @Override
    public Properties getTranslations(String languageTag) {
        return this.translationResources.get(languageTag);
    }
}
