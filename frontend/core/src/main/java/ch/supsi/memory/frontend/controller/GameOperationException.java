package ch.supsi.memory.frontend.controller;

public class GameOperationException extends RuntimeException {

    public enum Reason {BAD_FLIP, NO_GAME_LOADED, SAVE_FAILED, LOAD_FAILED}

    private final Reason reason;

    public GameOperationException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public GameOperationException(Reason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    public Reason getReason() {
        return this.reason;
    }
}
