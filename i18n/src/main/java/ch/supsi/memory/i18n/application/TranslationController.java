package ch.supsi.memory.i18n.application;

import ch.supsi.memory.i18n.business.TranslationPropertiesService;

import java.util.Properties;

public class TranslationController {

    private static TranslationController myself;

    private final TranslationService translationService;

    protected TranslationController() {
        this.translationService = TranslationPropertiesService.getInstance();
    }

    public static TranslationController getInstance() {
        if (myself == null) {
            myself = new TranslationController();
        }

        return myself;
    }

    public boolean setLanguageTag(String languageTag) {
        if (languageTag == null) {
            throw new IllegalArgumentException("bad input: languageTag=null");
        }

        return this.translationService.setLanguageTag(languageTag);
    }

    public String translate(String key) {
        if (key == null) {
            throw new IllegalArgumentException("bad input: key=null");
        }

        String value = this.translationService.translate(key);
        if (value == null) {
            return key;
        }
        return value;
    }

    public void pushTranslations(Properties translations, String forLanguageTag) {
        if (translations == null) {
            throw new IllegalArgumentException("bad input: translations=null");
        }
        if (forLanguageTag == null) {
            throw new IllegalArgumentException("bad input: forLanguageTag=null");
        }

        this.translationService.pushTranslations(translations, forLanguageTag);
    }
}
