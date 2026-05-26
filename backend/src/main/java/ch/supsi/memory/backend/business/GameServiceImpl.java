package ch.supsi.memory.backend.business;

import ch.supsi.memory.backend.application.GameService;
import ch.supsi.memory.backend.business.validate.GridCoordinatesRule;
import ch.supsi.memory.backend.dataaccess.GameBinaryDao;
import ch.supsi.memory.backend.model.CardModel;
import ch.supsi.memory.backend.model.Flippable;
import ch.supsi.memory.backend.model.GameModel;
import ch.supsi.memory.backend.model.GameObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServiceImpl implements GameService {

    public static final int MAX_HEIGHT = GridCoordinatesRule.MAX_Y + 1;
    public static final int MAX_WIDTH = GridCoordinatesRule.MAX_X + 1;

    private static GameServiceImpl myself;

    private final GameDao gameDao;
    private GameModel gameModel;

    private final Random random;
    private final List<GameObject> currentTurnFlips;
    private boolean currentTurnHasMismatchedCard;
    private boolean isDirty;

    protected GameServiceImpl() {
        this.gameDao = GameBinaryDao.getInstance();
        this.gameModel = null;

        this.random = new Random();
        this.currentTurnFlips = new ArrayList<>();
        this.currentTurnHasMismatchedCard = false;
        this.isDirty = false;
    }

    public static GameService getInstance() {
        if (myself == null) {
            myself = new GameServiceImpl();
        }

        return myself;
    }

    @Override
    public void newGame(int batchSize) {
        // NOTE: this proc. assumes that (MAX_HEIGHT*MAX_WIDTH) % batchSize == 0.
        // given our requirement to have a 8x6 grid and batchSize={2,3,4,6,8},
        // this constraint will always be satisfied.
        final GameObject[][] matrix = new GameObject[MAX_HEIGHT][MAX_WIDTH];
        final int n = MAX_HEIGHT * MAX_WIDTH;

        // populate matrix with correct batch of identical objects
        for (int i = 0; i < n; i++) {
            final char symbol = (char) ('A' + (i / batchSize));
            matrix[i / MAX_WIDTH][i % MAX_WIDTH] = new CardModel(symbol);
        }

        // Durstenfeld's variation of the Fisher and Yates shuffle algo.
        // cuts complexity down to O(n) instead of O(n^2).
        for (int i = n - 1; i > 0; i--) {
            final int j = this.random.nextInt(0, i + 1);

            GameObject temp = matrix[i / MAX_WIDTH][i % MAX_WIDTH];
            matrix[i / MAX_WIDTH][i % MAX_WIDTH] = matrix[j / MAX_WIDTH][j % MAX_WIDTH];
            matrix[j / MAX_WIDTH][j % MAX_WIDTH] = temp;
        }

        this.gameModel = new GameModel(
                batchSize,
                matrix);
        this.isDirty = true;
        // reset turn
        this.currentTurnFlips.clear();
        this.currentTurnHasMismatchedCard = false;
    }

    @Override
    public boolean flip(final int[] coords) {
        if (this.gameModel == null) {
            // since the game app starts with no loaded game, not having a loaded
            // game is not exceptional behavior, therefore no exception is thrown.
            return false;
        }

        // input is validated at controller level!
        final GameObject target = this.gameModel.getAt(coords);
        // just to be sure
        if (target == null) {
            return false;
        }
        // if the target is flipped, it must be inside our tracked flipped cards list.
        // Therefore, checking both is redudant. We must obviously ensure that all
        // flipped cards are tracked inside currentTurnFlips.
        if (target.isFlipped()) {
            // if the card is already flipped (symbol visible), do nothing.
            return false;
        }

        // defer flip all to the next turn start
        if (currentTurnFlips.size() == this.gameModel.getBatchSize()) {
            if (currentTurnHasMismatchedCard) {
                // a non-matching card was selected/flipped
                // -> flip back the flipped cards
                currentTurnFlips.forEach(GameObject::flip);
            }

            // all match, start new turn by clearing the flipped cards
            currentTurnFlips.clear();
            currentTurnHasMismatchedCard = false;
        }

        this.gameModel.flip(coords);
        this.isDirty = true;

        if (!currentTurnFlips.isEmpty()) {
            if (!currentTurnHasMismatchedCard && target.getSymbol() != currentTurnFlips.getFirst().getSymbol()) {
                currentTurnHasMismatchedCard = true;
            }
        }

        currentTurnFlips.add(target);

        return true;
    }

    @Override
    public boolean isDirty() {
        return this.isDirty;
    }

    public boolean save(Path path) {
        if (this.gameModel == null) {
            return false;
        }

        if (!this.currentTurnFlips.isEmpty()) {
            final int numFlipped = this.currentTurnFlips.size();
            final int currentBatchSize = this.gameModel.getBatchSize();

            // if we are in the middle of the turn or if our turn is complete
            // but has a non-matching card, flip back all flipped cards, save,
            // and then re-flip back the cards.
            // if instead our turn is complete and all cards match, we save the
            // flipped batch and start a new turn, the latter independently of having saved
            // successfully or not.
            if (numFlipped < currentBatchSize || numFlipped == currentBatchSize && currentTurnHasMismatchedCard) {
                this.currentTurnFlips.forEach(Flippable::flip);
                boolean rv = this.gameDao.write(this.gameModel, path);
                this.currentTurnFlips.forEach(Flippable::flip);

                this.isDirty = !rv;
                return rv;
            } else if (numFlipped == currentBatchSize && !currentTurnHasMismatchedCard) {
                // QOL
                boolean rv = this.gameDao.write(this.gameModel, path);
                this.currentTurnFlips.clear();
                this.currentTurnHasMismatchedCard = false;
                this.isDirty = !rv;
                return true;
            }
        }

        boolean rv = this.gameDao.write(this.gameModel, path);
        if (!rv) {
            return false;
        }

        this.isDirty = false;

        return true;
    }

    public GameModel load(Path path) {
        this.gameModel = this.gameDao.load(path);

        this.isDirty = false;
        // reset turn
        this.currentTurnFlips.clear();
        this.currentTurnHasMismatchedCard = false;

        return this.gameModel;
    }

    @Override
    public GameObject getAt(int[] coords) {
        return this.gameModel.getAt(coords);
    }

    @Override
    public int getCurrentTurnFlippedCount() {
        return this.currentTurnFlips.size();
    }

    @Override
    public int getBatchSize() {
        return this.gameModel.getBatchSize();
    }
}
