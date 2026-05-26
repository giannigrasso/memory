package ch.supsi.memory.frontend.view;

import java.nio.file.Path;
import java.util.Optional;

public interface FilePathProvider {

    Optional<Path> getPath();
}
