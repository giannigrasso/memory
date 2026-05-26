package ch.supsi.memory.backend.business.validate;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class UserPreferencesRule {

    public static final String KEY_BATCH_SIZE = "batch_size";
    public static final String KEY_LOCALE = "locale";

    public static final String ERROR;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(KEY_BATCH_SIZE).append("=").append(Arrays.toString(BatchSizeRule.ALLOWED_VALUES)).append("\n");
        sb.append(KEY_LOCALE).append("=").append(LocaleRule.ALLOWED_VALUES);
        ERROR = sb.toString();
    }

    public static boolean isValid(Properties prefs) {
        if (prefs == null || prefs.isEmpty()) {
            return false;
        }

        if (prefs.getProperty(KEY_BATCH_SIZE) == null) {
            return false;
        }
        if (prefs.getProperty(KEY_LOCALE) == null) {
            return false;
        }

        int batchSize;
        try {
            batchSize = Integer.parseInt(prefs.getProperty(KEY_BATCH_SIZE));
        } catch (NumberFormatException e) {
            return false;
        }
        if (!BatchSizeRule.isValid(batchSize)) {
            return false;
        }

        String locale = prefs.getProperty(KEY_LOCALE);
        if (!LocaleRule.isValid(locale)) {
            return false;
        }

        return true;
    }

    public static class BatchSizeRule {

        public static final int[] ALLOWED_VALUES = new int[]{2, 3, 4, 6, 8};

        public static final String ERROR = KEY_BATCH_SIZE + " must be one of " + Arrays.toString(ALLOWED_VALUES);

        public static boolean isValid(int batchSize) {
            return Arrays.stream(ALLOWED_VALUES)
                    .anyMatch(v -> v == batchSize);
        }
    }

    public static class LocaleRule {

        public static final List<String> ALLOWED_VALUES = List.of(
                "en-US",
                "it-CH");

        public static final String ERROR = KEY_LOCALE + " must be one of " + ALLOWED_VALUES;

        public static boolean isValid(String locale) {
            if (locale == null || locale.isBlank()) {
                return false;
            }

            return ALLOWED_VALUES.contains(locale);
        }
    }
}
