package ch.supsi.memory.backend.dataaccess;

import ch.supsi.memory.backend.business.GameDao;
import ch.supsi.memory.backend.dataaccess.serde.GameBinaryDeserializer;
import ch.supsi.memory.backend.dataaccess.serde.GameBinarySerializer;
import ch.supsi.memory.backend.dataaccess.serde.GameDeserializer;
import ch.supsi.memory.backend.dataaccess.serde.GameSerializer;
import ch.supsi.memory.backend.model.GameModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameBinaryDao implements GameDao {

    private static GameBinaryDao myself;

    private final GameSerializer gameSerializer;

    private final GameDeserializer gameDeserializer;

    protected GameBinaryDao(GameSerializer gameSerializer, GameDeserializer gameDeserializer) {
        this.gameSerializer = gameSerializer;
        this.gameDeserializer = gameDeserializer;
    }

    protected GameBinaryDao() {
        this(new GameBinarySerializer(),
                new GameBinaryDeserializer());
    }

    public static GameDao getInstance() {
        if (myself == null) {
            myself = new GameBinaryDao();
        }

        return myself;
    }

    @Override
    public GameModel load(Path saveFilePath) throws DataAccessException {
        try {
            byte[] data = Files.readAllBytes(saveFilePath);
            return this.gameDeserializer.deserialize(data);
        } catch (IOException e) {
            throw new DataAccessException("failed to load save file " + saveFilePath.toAbsolutePath(), e);
        }
    }

    @Override
    public boolean write(GameModel model, Path saveFilePath) throws DataAccessException {
        try {
            byte[] data = this.gameSerializer.serialize(model);
            Files.write(saveFilePath, data);
        } catch (IOException e) {
            throw new DataAccessException("failed to write save file " + saveFilePath.toAbsolutePath(), e);
        }

        return true;
    }
}
