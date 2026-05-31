package ch.supsi.memory.i18n.business;

import ch.supsi.memory.i18n.application.TranslationService;
import ch.supsi.memory.i18n.dataaccess.TranslationPropertiesDao;

import java.util.Properties;

public class TranslationPropertiesService implements TranslationService {

    public static final String DEFAULT_LANGUAGE_TAG = "en-US";

    private static TranslationService myself;

    private final TranslationDao translationDao;

    private String languageTag;

    private Properties translations;

    protected TranslationPropertiesService() {
        this.translationDao = TranslationPropertiesDao.getInstance();

        this.translations = new Properties();
        this.languageTag = DEFAULT_LANGUAGE_TAG;
        this.translationDao.pushTranslations(this.translations, this.languageTag);
    }

    public static TranslationService getInstance() {
        if (myself == null) {
            myself = new TranslationPropertiesService();
        }

        return myself;
    }

    @Override
    public boolean isLanguageTagSupported(String languageTag) {
        return this.translationDao.getSupportedLanguageTags().contains(languageTag);
    }

    @Override
    public boolean setLanguageTag(String languageTag) {
        if (!this.isLanguageTagSupported(languageTag)) {
            return false;
        }

        this.languageTag = languageTag;

        return true;
    }

    @Override
    public String translate(String key) {
        this.translations = this.translationDao.getTranslations(this.languageTag);
        return this.translations.getProperty(key);
    }

    @Override
    public void pushTranslations(Properties translations, String forLanguageTag) {
        this.translationDao.pushTranslations(translations, forLanguageTag);
    }
}
