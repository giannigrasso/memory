package ch.supsi.memory.backend.dataaccess.serde;

import ch.supsi.memory.backend.dataaccess.DataAccessException;
import ch.supsi.memory.backend.model.GameModel;

public interface GameDeserializer {

    GameModel deserialize(byte[] data) throws DataAccessException;
}
