package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.model.TranslationProvider;

public interface CliCommandFactory {

    class CliCommandException extends Exception {

        private final String translationKey;

        public CliCommandException(String message, Throwable cause) {
            super(message, cause);
            this.translationKey = message;
        }

        public CliCommandException(String message) {
            super(message);
            this.translationKey = message;
        }

        public CliCommandException(String message, String translationKey, Throwable cause) {
            super(message, cause);
            this.translationKey = translationKey;
        }

        public CliCommandException(String message, String translationKey) {
            super(message);
            this.translationKey = translationKey;
        }

        public String getTranslationKey() {
            return translationKey;
        }

        public String getLocalizedMessage(TranslationProvider translator) {
            return translator.translate(this.translationKey);
        }
    }

    Command create(String[] args) throws CliCommandException;

    String getUsage();
}
