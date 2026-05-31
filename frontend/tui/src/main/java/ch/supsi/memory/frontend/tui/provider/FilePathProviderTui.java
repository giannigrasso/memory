package ch.supsi.memory.frontend.tui.provider;

import ch.supsi.memory.frontend.view.FilePathProvider;

import java.nio.file.Path;
import java.util.Optional;

public class FilePathProviderTui implements FilePathProvider {

    private String[] args;

    public FilePathProviderTui(String[] args) {
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
        // TODO: fix .mem constraint
        final String pathStr = !pathTrimmed.endsWith(".json")
                ? pathTrimmed + ".json"
                : pathTrimmed;

        return Optional.of(Path.of(pathStr));
    }
}
