package ch.supsi.memory.frontend.model;

import ch.supsi.memory.backend.application.PreferencesController;


import java.util.Properties;


public class PreferencesModel extends AbstractModel implements PreferencesEventHandler {

    private static PreferencesModel myself;

    final private PreferencesController backend;

    private boolean isShowing;

    final private int batchSize;

    final private String locale;

    protected PreferencesModel() {
        super();

        this.backend = PreferencesController.getInstance();
        this.batchSize = backend.getBatchSize();
        this.locale = backend.getLocale();
    }

    public static PreferencesModel getInstance() {
        if (myself == null) {
            myself = new PreferencesModel();
        }
        return myself;
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setIsShowing(boolean showing) {
        isShowing = showing;
    }

    @Override
    public void editPreferences(Properties properties) {
        // since properties are cold reloaded, we do not update our model.
        backend.editPreferences(properties);
        backend.persist();
    }

    @Override
    public int getBatchSize() {
        return batchSize;
    }

    @Override
    public String getLocale() {
        return locale;
    }
}
