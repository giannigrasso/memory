package ch.supsi.memory.frontend.controller;

import ch.supsi.memory.frontend.model.PreferencesEventHandler;
import ch.supsi.memory.frontend.model.PreferencesModel;
import ch.supsi.memory.frontend.view.DataView;

import java.util.List;
import java.util.Properties;


public class PreferencesController implements PreferencesEventController {

    private static PreferencesController myself;

    final private PreferencesEventHandler preferencesModel;

    private List<DataView> views;

    protected PreferencesController() {
        this.preferencesModel = PreferencesModel.getInstance();
    }

    public static PreferencesController getInstance() {
        if (myself == null) {
            myself = new PreferencesController();
        }
        return myself;
    }

    public void initialize(List<DataView> views) {
        this.views = views;
    }

    public void showPreferences() {
        this.preferencesModel.setIsShowing(true);

        this.views.forEach(DataView::update);
    }

    @Override
    public void editPreferences(Properties properties) {
        this.preferencesModel.editPreferences(properties);
        this.preferencesModel.setIsShowing(false);

        this.views.forEach(DataView::update);
    }

    @Override
    public String getLocale() {
        return this.preferencesModel.getLocale();
    }

    @Override
    public int getBatchSize() {
        return this.preferencesModel.getBatchSize();
    }
}
