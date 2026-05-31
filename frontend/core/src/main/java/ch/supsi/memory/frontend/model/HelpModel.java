package ch.supsi.memory.frontend.model;

public class HelpModel extends AbstractModel implements HelpEventHandler {

    private static HelpModel myself;

    protected HelpModel() {
    }

    public static HelpModel getInstance() {
        if (myself == null) {
            myself = new HelpModel();
        }

        return myself;
    }

    @Override
    public String getTitle() {
        return "label.help.title";
    }

    @Override
    public String getRulesTitle() {
        return "label.help.rules.title";
    }

    @Override
    public String getRulesHeader() {
        return "label.help.rules.header";
    }

    @Override
    public String getRulesGoal() {
        return "label.help.rules.goal";
    }

    @Override
    public String getRulesTurn() {
        return "label.help.rules.turn";
    }

    @Override
    public String getRulesMatch() {
        return "label.help.rules.match";
    }

    @Override
    public String getRulesMismatch() {
        return "label.help.rules.mismatch";
    }

    @Override
    public String getFeatHeader() {
        return "label.help.features.header";
    }

    @Override
    public String getFeatNew() {
        return "label.help.features.new";
    }

    @Override
    public String getFeatSave() {
        return "label.help.features.save";
    }

    @Override
    public String getFeatLoad() {
        return "label.help.features.load";
    }

    @Override
    public String getFeatPreferences() {
        return "label.help.features.preferences";
    }

    @Override
    public String getFeatLanguage() {
        return "label.help.features.language";
    }

    @Override
    public String getHowHeader() {
        return "label.help.how.header";
    }

    @Override
    public String getHowNewGame() {
        return "label.help.how.newgame";
    }

    @Override
    public String getHowFlip() {
        return "label.help.how.flip";
    }

    @Override
    public String getHowSave() {
        return "label.help.how.save";
    }

    @Override
    public String getHowLoad() {
        return "label.help.how.load";
    }
}
