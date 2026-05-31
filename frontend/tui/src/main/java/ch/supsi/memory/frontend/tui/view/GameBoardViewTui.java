package ch.supsi.memory.frontend.tui.view;


import ch.supsi.memory.backend.model.GameObject;
import ch.supsi.memory.frontend.command.CommandRegistry;
import ch.supsi.memory.frontend.model.AbstractModel;
import ch.supsi.memory.frontend.model.GameModel;
import ch.supsi.memory.frontend.model.TranslationProvider;
import ch.supsi.memory.frontend.view.ControlledView;


public class GameBoardViewTui implements ControlledView {

    private static GameBoardViewTui myself;

    private TranslationProvider translator;

    private GameModel gameModel;

    private int gridH;
    private int gridW;

    private GameObject[][] gridButtons;


    public static GameBoardViewTui getInstance() {
        if (myself == null) {
            myself = new GameBoardViewTui();

        }
        return myself;
    }


    @Override
    public void initialize(CommandRegistry commands, AbstractModel model, TranslationProvider translator) {
        this.translator = translator;
        this.gameModel = (GameModel) model;
        gridH = gameModel.getGridHeight();
        gridW = gameModel.getGridWidth();
    }


    @Override
    public void update() {
        StringBuilder sb = new StringBuilder();

        sb.append('╭');
        sb.append("───┬".repeat(gridW - 1));
        sb.append("───╮");
        sb.append('\n');

        for (int row = 0; row < gridH; row++) {
            sb.append("│ ");
            for (int column = 0; column < gridW; column++) {
                final GameObject card = this.gameModel.getAt(new int[]{column, row});
                String icon = card.isFlipped() ? "" + card.getSymbol() : "?";
                ;
                sb.append(icon);
                sb.append(" │ ");
            }
            sb.append('\n');

            if (row < gridH - 1) {
                sb.append("├");
                sb.append("───┼".repeat(gridW - 1));
                sb.append("───");
                sb.append("┤");
                sb.append('\n');
            }
        }

        sb.append('╰');
        sb.append("───┴".repeat(gridW - 1));
        sb.append("───╯");

        System.out.println(sb);
    }


}
