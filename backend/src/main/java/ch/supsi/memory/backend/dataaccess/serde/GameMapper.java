package ch.supsi.memory.backend.dataaccess.serde;

import ch.supsi.memory.backend.model.GameModel;
import ch.supsi.memory.backend.model.GameObject;

import java.util.ArrayList;

import static ch.supsi.memory.backend.dataaccess.serde.GameBinaryFormat.FILE_TAG;

public class GameMapper {

    public static GameDTO toDTO(GameModel game) {

        GameDTO dto = new GameDTO();

        dto.batchSize = game.getBatchSize();
        dto.rows = game.getRows();
        dto.columns = game.getColumns();

        dto.cards = new ArrayList<>();

        for (int i = 0; i < game.getRows(); i++) {
            for (int j = 0; j < game.getColumns(); j++) {

                GameObject go = game.getAt(new int[]{j, i});

                GameDTO.CardDTO c = new GameDTO.CardDTO();
                c.x = j;
                c.y = i;
                c.symbol = go.getSymbol();
                c.flipped = go.isFlipped();

                dto.cards.add(c);
            }
        }

        return dto;
    }
}