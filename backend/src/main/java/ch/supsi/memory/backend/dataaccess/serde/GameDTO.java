package ch.supsi.memory.backend.dataaccess.serde;

import java.util.List;

public class GameDTO {

    public int batchSize;
    public int rows;
    public int columns;

    public List<CardDTO> cards;

    public static class CardDTO {
        public int x;
        public int y;
        public char symbol;
        public boolean flipped;
    }
}