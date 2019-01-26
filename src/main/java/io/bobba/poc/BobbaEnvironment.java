package io.bobba.poc;

import io.bobba.poc.core.Game;

public class BobbaEnvironment {
    private static BobbaEnvironment instance;

    private Game game;

    private BobbaEnvironment() {
        this.game = new Game();
    }

    public static BobbaEnvironment getEnvironment() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new BobbaEnvironment();
    }
}
