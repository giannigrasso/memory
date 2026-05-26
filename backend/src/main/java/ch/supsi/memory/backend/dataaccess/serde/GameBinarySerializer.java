package ch.supsi.memory.backend.dataaccess.serde;

import ch.supsi.memory.backend.dataaccess.DataAccessException;
import ch.supsi.memory.backend.model.GameModel;
import ch.supsi.memory.backend.model.GameObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import static ch.supsi.memory.backend.dataaccess.serde.GameBinaryFormat.FILE_TAG;

public class GameBinarySerializer implements GameSerializer {

    @Override
    public byte[] serialize(GameModel game) throws DataAccessException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (final DataOutputStream writer = new DataOutputStream(os)) {
            writer.writeChar(FILE_TAG);
            writer.writeInt(game.getBatchSize());
            writer.writeInt(game.getRows());
            writer.writeInt(game.getColumns());

            BitSet bs = new BitSet();
            for (int i = 0; i < game.getRows(); i++) {
                for (int j = 0; j < game.getColumns(); j++) {
                    GameObject go = game.getAt(new int[]{j, i});
                    writer.writeChar(go.getSymbol());

                    int bitIndex = i * game.getColumns() + j;
                    if (go.isFlipped()) {
                        bs.set(bitIndex, true);
                    }
                }
            }

            byte[] leFlipBytes = bs.toByteArray();
            writer.write(leFlipBytes);

            writer.flush();
            // autoclose

            return os.toByteArray();
        } catch (IOException e) {
            throw new DataAccessException("failed to serialize game", e);
        }
    }
}
