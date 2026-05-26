package ch.supsi.memory.frontend.command;

import ch.supsi.memory.frontend.controller.*;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private final Map<Class<? extends Command>, Command> commands;

    private void register(Command cmd) {
        commands.put(cmd.getClass(), cmd);
    }

    public CommandRegistry(
            GameEventController gameEventController,
            UserFeedbackEventController userFeedbackEventController,
            PreferencesEventController preferencesEventController,
            QuitEventController quitEventController,
            AboutEventController aboutEventController,
            HelpEventController helpEventController
    ) {
        this.commands = new HashMap<>();

        register(new NewGameCommand(gameEventController, preferencesEventController::getBatchSize));
        register(new SaveCommand(gameEventController));
        register(new SaveAsCommand(gameEventController));
        register(new FlipCommand(gameEventController));
        register(new AboutCommand(aboutEventController));
        register(new HelpCommand(helpEventController));
        register(new LoadGameCommand(gameEventController));
        register(new QuitCommand(quitEventController));
        register(new FeedbackCommand(userFeedbackEventController));
        register(new ShowPreferencesCommand(preferencesEventController));
        register(new EditPreferencesCommand(preferencesEventController));
    }

    public <T extends Command> T get(Class<T> commandClass) {
        return commandClass.cast(this.commands.get(commandClass));
    }
}
