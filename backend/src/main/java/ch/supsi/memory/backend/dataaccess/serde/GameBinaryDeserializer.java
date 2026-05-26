package ch.supsi.memory.backend.dataaccess.serde;

import ch.supsi.memory.backend.dataaccess.DataAccessException;
import ch.supsi.memory.backend.model.CardModel;
import ch.supsi.memory.backend.model.GameModel;
import ch.supsi.memory.backend.model.GameObject;

import java.nio.ByteBuffer;

import static ch.supsi.memory.backend.dataaccess.serde.GameBinaryFormat.*;

public class GameBinaryDeserializer implements GameDeserializer {

    @Override
    public GameModel deserialize(final byte[] data) throws DataAccessException {
        final ByteBuffer buff = ByteBuffer.wrap(data);


        char tag = readTag(buff);
        if (tag != FILE_TAG) {
            throw new DataAccessException("bad tag. file is not a valid .mem");
        }
        final int batchSize = readBatchSize(buff);
        final int rows = readRowsCount(buff);
        final int cols = readColumnsCount(buff);
        final boolean[][] flipped = readFlippedStates(buff, rows, cols);

        final GameObject[][] gos = new GameObject[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final char symbol = readSymbolAt(buff, cols, i, j);
                GameObject go = new CardModel(symbol);
                if (flipped[i][j]) {
                    go.flip();
                }
                gos[i][j] = go;
            }
        }

        return new GameModel(batchSize, gos);
    }

    private int getFlippedGridOffset(int rows, int cols) {
        return OFFSET_SYMBOLS + Character.BYTES * (rows * cols);
    }

    private char readTag(ByteBuffer buff) throws DataAccessException {
        try {
            return buff.getChar(OFFSET_TAG);
        } catch (IndexOutOfBoundsException e) {
            throw new DataAccessException("failed to read tag at offset " + OFFSET_TAG, e);
        }
    }

    private int readBatchSize(ByteBuffer buff) throws DataAccessException {
        try {
            return buff.getInt(OFFSET_BATCH_SIZE);
        } catch (IndexOutOfBoundsException e) {
            throw new DataAccessException("failed to read batch_size (int) at offset " + OFFSET_BATCH_SIZE, e);
        }
    }

    private int readRowsCount(ByteBuffer buff) throws DataAccessException {
        try {
            return buff.getInt(OFFSET_ROWS_COUNT);
        } catch (IndexOutOfBoundsException e) {
            throw new DataAccessException("failed to read rows count (int) at offset " + OFFSET_ROWS_COUNT, e);
        }
    }

    private int readColumnsCount(ByteBuffer buff) throws DataAccessException {
        try {
            return buff.getInt(OFFSET_COLS_COUNT);
        } catch (IndexOutOfBoundsException e) {
            throw new DataAccessException("failed to read columns count (int) at offset " + OFFSET_COLS_COUNT, e);
        }
    }

    private char readSymbolAt(ByteBuffer buff, int cols, int row, int col) throws DataAccessException {
        final int index = (row * cols + col) * 2;
        try {
            return buff.getChar(OFFSET_SYMBOLS + index);
        } catch (IndexOutOfBoundsException e) {
            String msg = String.format(
                    "failed to read symbol (char) at row %d col %d at offset %d",
                    row,
                    col,
                    OFFSET_SYMBOLS + index);
            throw new DataAccessException(msg, e);
        }
    }

    private boolean[][] readFlippedStates(ByteBuffer buff, int rows, int cols) throws DataAccessException {
        final int offset = getFlippedGridOffset(rows, cols);
        final boolean[][] flipped = new boolean[rows][cols];
        final byte[] leFlipDataBytes = new byte[buff.limit() - offset];
        try {
            buff.get(offset, leFlipDataBytes);
        } catch (IndexOutOfBoundsException e) {
            throw new DataAccessException("failed to read flipped states byte at offset " + offset, e);
        }

        final int max = Math.min(rows * cols, leFlipDataBytes.length * 8);
        for (int i = 0; i < max; i++) {
            final byte flipDataByte = leFlipDataBytes[i / 8];
            final int isFlippedBit = (flipDataByte >> (i % 8)) & 0x01;
            if (isFlippedBit == 1) {
                flipped[i / cols][i % cols] = true;
            }
        }

        return flipped;
    }
}
