package ch.supsi.memory.frontend.view;

import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.TranslationProvider;

public interface ControlledView extends DataView {

    void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator);

}
