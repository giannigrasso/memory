package ch.supsi.memory.frontend.javafx.view.modal;

import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.FilePathProvider;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class LoadGameModal implements FilePathProvider {

    private final TranslationProvider translator;

    public LoadGameModal(TranslationProvider translator) {
        this.translator = translator;
    }

    @Override
    public Optional<Path> getPath() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(this.translator.translate("label.load_game.title"));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Memory Game files (*.mem)", "*.mem");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File rv = fileChooser.showOpenDialog(null);
        return rv != null
                ? Optional.of(rv.toPath())
                : Optional.empty();
    }
}
