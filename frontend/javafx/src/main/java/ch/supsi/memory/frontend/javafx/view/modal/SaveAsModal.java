package ch.supsi.memory.frontend.javafx.view.modal;

import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.FilePathProvider;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class SaveAsModal implements FilePathProvider {

    private final TranslationProvider translator;

    public SaveAsModal(TranslationProvider translator) {
        this.translator = translator;
    }

    @Override
    public Optional<Path> getPath() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(this.translator.translate("label.saveas"));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JSON (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File rv = fileChooser.showSaveDialog(null);
        return rv != null
                ? Optional.of(rv.toPath())
                : Optional.empty();
    }
}
