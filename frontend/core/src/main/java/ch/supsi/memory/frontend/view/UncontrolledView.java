package ch.supsi.memory.frontend.view;

import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.TranslationProvider;

public interface UncontrolledView extends DataView {

    void initialize(AbstractModel model, TranslationProvider translator);

}
