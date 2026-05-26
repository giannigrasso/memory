package ch.supsi.memory.backend.model;

public class GameModel {

    private final int batchSize;

    private final GameObject[][] gameObjects;

    public GameModel(int batchSize, GameObject[][] gameObjects) {
        this.batchSize = batchSize;
        this.gameObjects = gameObjects;
    }

    public int getRows() {
        return this.gameObjects.length;
    }

    public int getColumns() {
        return this.gameObjects[0].length;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public GameObject getAt(final int[] coords) {
        // TODO: make final
        return this.gameObjects[coords[1]][coords[0]];
    }

    public boolean flip(final int[] coords) {
        final GameObject obj = this.gameObjects[coords[1]][coords[0]];
        if (obj == null) {
            return false;
        }

        obj.flip();

        return true;
    }
}
