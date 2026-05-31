package ch.supsi.memory.backend.dataaccess.serde;

import ch.supsi.memory.backend.dataaccess.DataAccessException;
import ch.supsi.memory.backend.model.CardModel;
import ch.supsi.memory.backend.model.GameModel;
import ch.supsi.memory.backend.model.GameObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;

public class GameJsonDeserializer implements GameDeserializer {

    @Override
    public GameModel deserialize(byte[] data) throws DataAccessException {

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            GameDTO dto = objectMapper.readValue(new ByteArrayInputStream(data), GameDTO.class);

            GameObject[][] gos = new GameObject[dto.rows][dto.columns];

            for (GameDTO.CardDTO c : dto.cards) {

                GameObject go = new CardModel(c.symbol);

                if (c.flipped) {
                    go.flip();
                }

                gos[c.y][c.x] = go;
            }

            return new GameModel(dto.batchSize, gos);

        } catch (Exception e) {
            throw new DataAccessException("failed to deserialize JSON game", e);
        }

    }
}