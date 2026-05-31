package ch.supsi.memory.frontend.tui.view;

import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.HelpEventHandler;
import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.UncontrolledView;


public class HelpViewTui implements UncontrolledView {

    private static HelpViewTui myself;

    private HelpEventHandler helpModel;

    private String titleLabel;
    private String rulesTitle;
    private String rulesContent;
    private String featuresTitle;
    private String featuresContent;
//    private  String howTitle;
//    private  String howContent;

    protected HelpViewTui() {

    }

    public static HelpViewTui getInstance() {
        if (myself == null) {
            myself = new HelpViewTui();
        }

        return myself;
    }


    @Override
    public void initialize(AbstractModel model, TranslationProvider translator) {
        this.helpModel = (HelpEventHandler) model;
        this.applyTranslations(translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        titleLabel = translator.translate(helpModel.getRulesTitle());
        rulesTitle = translator.translate(helpModel.getRulesHeader());

        rulesContent = "• " + translator.translate(helpModel.getRulesGoal()) + "\n" +
                "• " + translator.translate(helpModel.getRulesTurn()) + "\n" +
                "• " + translator.translate(helpModel.getRulesMatch()) + "\n" +
                "• " + translator.translate(helpModel.getRulesMismatch());

        featuresTitle = translator.translate(helpModel.getFeatHeader());

        featuresContent = "• about           " + translator.translate("label.help.features.about") + "\n" +
                "• flip            " + translator.translate("label.help.features.flip") + "\n" +
                "• help            " + translator.translate("label.help.features.help") + "\n" +
                "• load            " + translator.translate(helpModel.getFeatLoad()) + "\n" +
                "• new             " + translator.translate(helpModel.getFeatNew()) + "\n" +
                "• prefs           " + translator.translate(helpModel.getFeatPreferences()) + "\n" +
                "• quit            " + translator.translate("label.help.features.quit") + "\n" +
                "• save / saveas   " + translator.translate(helpModel.getFeatSave());
    }

    @Override
    public void update() {
        System.out.println(titleLabel);
        System.out.println();
        System.out.println(rulesTitle);
        System.out.println(rulesContent);
        System.out.println();
        System.out.println(featuresTitle);
        System.out.println(featuresContent);
        System.out.println();
    }
}
