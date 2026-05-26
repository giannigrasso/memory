package ch.supsi.memory.backend.dataaccess.serde;

public class GameBinaryFormat {

    // tag=ME
    public static final char FILE_TAG = 0x4D45;

    public static final int OFFSET_TAG = 0;
    public static final int OFFSET_BATCH_SIZE = OFFSET_TAG + Character.BYTES;
    public static final int OFFSET_ROWS_COUNT = OFFSET_BATCH_SIZE + Integer.BYTES;
    public static final int OFFSET_COLS_COUNT = OFFSET_ROWS_COUNT + Integer.BYTES;
    public static final int OFFSET_SYMBOLS = OFFSET_COLS_COUNT + Integer.BYTES;
}
