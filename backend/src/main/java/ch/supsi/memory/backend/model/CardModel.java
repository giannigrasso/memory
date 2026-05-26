package ch.supsi.memory.backend.model;

public class CardModel implements GameObject {

    private final char symbol;

    private boolean isFlipped;

    public CardModel(char symbol) {
        this.symbol = symbol;
        this.isFlipped = false;
    }

    @Override
    public void flip() {
        isFlipped = !isFlipped;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
}
