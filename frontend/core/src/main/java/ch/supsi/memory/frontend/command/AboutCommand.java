package ch.supsi.memory.frontend.command;


import ch.supsi.memory.frontend.controller.AboutEventController;

public class AboutCommand implements Command {

    final private AboutEventController receiver;

    public AboutCommand(AboutEventController receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.showAbout();
    }
}
