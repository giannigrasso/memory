package ch.supsi.memory.frontend.view;

import ch.supsi.memory.frontend.command.Command;
import ch.supsi.memory.frontend.model.AbstractModel;

import java.util.HashMap;

public interface ControlledView extends DataView {

    void initialize(HashMap<String, Command> commands, AbstractModel model);

}
