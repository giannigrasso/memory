package ch.supsi.memory.frontend.controller;

import ch.supsi.memory.frontend.view.DataView;

import java.util.List;

public class AboutController implements AboutEventController {

    private static AboutController myself;

    private List<DataView> views;

    protected AboutController() {
    }

    public static AboutController getInstance() {
        if (myself == null) {
            myself = new AboutController();
        }

        return myself;
    }

    public void initialize(List<DataView> views) {
        this.views = views;
    }

    @Override
    public void showAbout() {
        this.views.forEach(DataView::update);
    }
}
