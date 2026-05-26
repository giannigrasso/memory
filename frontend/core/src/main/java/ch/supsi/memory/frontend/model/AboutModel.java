package ch.supsi.memory.frontend.model;

public class AboutModel extends AbstractModel implements AboutEventHandler {

    private static AboutModel myself;

    protected AboutModel() {
    }

    public static AboutModel getInstance() {
        if (myself == null) {
            myself = new AboutModel();
        }
       
        return myself;
    }
}
