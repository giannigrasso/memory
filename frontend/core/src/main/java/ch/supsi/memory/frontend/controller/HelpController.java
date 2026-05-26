package ch.supsi.memory.frontend.controller;

import ch.supsi.memory.frontend.model.HelpEventHandler;
import ch.supsi.memory.frontend.model.HelpModel;
import ch.supsi.memory.frontend.view.DataView;

import java.util.List;

public class HelpController implements HelpEventController {

    private static HelpController myself;

    private HelpEventHandler helpModel;
    private List<DataView> views;

    protected HelpController() {
        this.helpModel = HelpModel.getInstance();
    }

    public static HelpController getInstance() {
        if (myself == null) {
            myself = new HelpController();
        }

        return myself;
    }

    public void initialize(List<DataView> views) {
        this.views = views;
    }

    @Override
    public void showHelp() {
        this.views.forEach(DataView::update);
    }
}
