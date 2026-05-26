package ch.supsi.memory.frontend.tui.cli;

import ch.supsi.memory.frontend.command.Command;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class CliCommandHandler {

    private final Scanner input;

    private final Map<String, CliCommandFactory> factories;

    public CliCommandHandler(Scanner input, Map<String, CliCommandFactory> factories) {
        this.input = input;
        this.factories = factories;
    }

    public void handleLine() throws CliCommandFactory.CliCommandException {
        System.out.print("> ");
        final String line = input.nextLine();

        if (line == null || line.isBlank()) return;

        final String trimmed = line.trim().toLowerCase();
        final String[] args = trimmed.split(" ");
        final String command = args[0];

        final CliCommandFactory factory = factories.get(command);
        if (factory == null) {
            throw new CliCommandFactory.CliCommandException("command not found");
        }
        Command cliCommand = factory.create(Arrays.copyOfRange(args, 1, args.length));
        cliCommand.execute();
    }
}
