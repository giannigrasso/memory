package ch.supsi.memory.frontend.tui.view;

import ch.supsi.memory.frontend.view.ControlledView;

import java.util.Scanner;

public interface ControlledTuiView extends ControlledView {

    void setInput(Scanner input);
}
