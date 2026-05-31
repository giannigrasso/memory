package ch.supsi.memory.frontend.tui.view;

import ch.supsi.memory.frontend.model.AboutEventHandler;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.UncontrolledView;

import java.time.format.DateTimeFormatter;

public class AboutViewTui implements UncontrolledView {

    private static AboutViewTui myself;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private AboutEventHandler aboutModel;

    private String nameLabel;
    private String frontendVersionLabel;
    private String frontendBuildLabel;
    private String backendVersionLabel;
    private String backendBuildLabel;
    private String devLabel;
    private String devContent;
    private String copyrightLabel;

    protected AboutViewTui() {
    }

    public static AboutViewTui getInstance() {
        if (myself == null) {
            myself = new AboutViewTui();
        }
        return myself;
    }

    @Override
    public void initialize(AbstractModel model, TranslationProvider translator) {
        this.aboutModel = (AboutEventHandler) model;
        this.applyTranslations(translator);
    }

    private void applyTranslations(TranslationProvider translator) {
        nameLabel = aboutModel.getFrontendName();
        frontendVersionLabel = translator.translate(aboutModel.getVersionFrontendKey()) + aboutModel.getVersionFrontend();
        frontendBuildLabel = translator.translate(aboutModel.getBuildDateFrontendKey()) + aboutModel.getBuildDateFrontend().format(DATE_FORMATTER);
        backendVersionLabel = translator.translate(aboutModel.getVersionBackendKey()) + aboutModel.getVersionBackend();
        backendBuildLabel = translator.translate(aboutModel.getBuildDateBackendKey()) + aboutModel.getBuildDateBackend().format(DATE_FORMATTER);
        devLabel = translator.translate(aboutModel.getDevelopersKey());
        final StringBuilder devs = new StringBuilder();
        for (String dev : aboutModel.getDevelopers()) {
            devs.append(dev).append("\n");
        }
        devs.deleteCharAt(devs.length() - 1);
        devContent = devs.toString();
        copyrightLabel = translator.translate(aboutModel.getCopyrightKey());
    }

    @Override
    public void update() {
        System.out.println("=== " + nameLabel + " ===");
        System.out.println();
        System.out.println("Frontend");
        System.out.println(frontendVersionLabel);
        System.out.println(frontendBuildLabel);
        System.out.println();
        System.out.println("Backend");
        System.out.println(backendVersionLabel);
        System.out.println(backendBuildLabel);
        System.out.println();
        System.out.println(devLabel);
        System.out.println(devContent);
        System.out.println();
        System.out.println(copyrightLabel);
    }
}