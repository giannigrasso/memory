package ch.supsi.memory.frontend.tui;

import ch.supsi.memory.frontend.view.FilePathProvider;

import java.nio.file.Path;
import java.util.Optional;

public class CliFilePathProvider implements FilePathProvider {

    private String[] args;

    public CliFilePathProvider(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Optional<Path> getPath() {
        if (args == null || args.length == 0 || args[0].isBlank()) {
            return Optional.empty();
        }

        final String pathTrimmed = args[0].trim();
        final String pathStr = !pathTrimmed.endsWith(".mem")
                ? pathTrimmed + ".mem"
                : pathTrimmed;

        return Optional.of(Path.of(pathStr));
    }
}
