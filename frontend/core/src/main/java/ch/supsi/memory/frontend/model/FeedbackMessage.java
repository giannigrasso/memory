package ch.supsi.memory.frontend.model;

import java.util.Map;

public class FeedbackMessage {
    public enum Type {
        NEW_GAME, SAVE_OK, SAVE_FAILED,
        LOAD_OK, LOAD_FAILED,
        FLIPPED, BAD_FLIP, NO_GAME_LOADED,
        UNKNOWN_ERROR, CLEAR
    }

    private final Type type;
    private final Map<String, Object> params;

    private FeedbackMessage(Type type, Map<String, Object> params) {
        this.type = type;
        this.params = params;
    }

    public static FeedbackMessage newGame() {
        return new FeedbackMessage(Type.NEW_GAME, Map.of());
    }

    public static FeedbackMessage saveOk(String path) {
        return new FeedbackMessage(Type.SAVE_OK, Map.of("path", path));
    }

    public static FeedbackMessage saveFailed() {
        return new FeedbackMessage(Type.SAVE_FAILED, Map.of());
    }

    public static FeedbackMessage loadOk() {
        return new FeedbackMessage(Type.LOAD_OK, Map.of());
    }

    public static FeedbackMessage loadFailed() {
        return new FeedbackMessage(Type.LOAD_FAILED, Map.of());
    }

    public static FeedbackMessage badFlip() {
        return new FeedbackMessage(Type.BAD_FLIP, Map.of());
    }

    public static FeedbackMessage noGameLoaded() {
        return new FeedbackMessage(Type.NO_GAME_LOADED, Map.of());
    }

    public static FeedbackMessage unknownError() {
        return new FeedbackMessage(Type.UNKNOWN_ERROR, Map.of());
    }

    public static FeedbackMessage clear() {
        return new FeedbackMessage(Type.CLEAR, Map.of());
    }

    public static FeedbackMessage flipped(int count, int max) {
        return new FeedbackMessage(Type.FLIPPED, Map.of("count", count, "max", max));
    }

    public Type getType() {
        return type;
    }

    public <T> T getParam(String key) {
        // :sob:
        return (T) params.get(key);
    }
}