package ch.supsi.memory.backend.dataaccess.serde;

import ch.supsi.memory.backend.dataaccess.DataAccessException;
import ch.supsi.memory.backend.model.GameModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;

public class GameJsonSerializer implements GameSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(GameModel game) throws DataAccessException {

        try {
            GameDTO dto = GameMapper.toDTO(game);

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(os, dto);

            return os.toByteArray();

        } catch (Exception e) {
            throw new DataAccessException("failed to serialize game to JSON", e);
        }
    }
}