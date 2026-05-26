package ch.supsi.memory.frontend.javafx.view;


import ch.supsi.memory.frontend.view.ControlledView;
import javafx.scene.Node;

public interface ControlledFxView extends ControlledView {

    Node getNode();

}
