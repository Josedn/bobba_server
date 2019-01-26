package io.bobba.poc;

import io.bobba.poc.core.Game;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class BobbaEnvironment {
    private static BobbaEnvironment instance;

    private Game game;

    private BobbaEnvironment() {
    	System.out.println("");
        System.out.println("|         |    |          o     ");
        System.out.println("|---.,---.|---.|---.,---. .,---.");
        System.out.println("|   ||   ||   ||   |,---| ||   |");
        System.out.println("`---'`---'`---'`---'`---^o``---'");
        System.out.println("Copyright (c) 2019 - relevance");
        System.out.println();
        Logging.getInstance().setLogLevel(LogLevel.Verbose);
        
        this.game = new Game();
        Logging.getInstance().writeLine("The environment has initialized successfully. Ready for connections.", LogLevel.Verbose);
        
        
    }

    public static BobbaEnvironment getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new BobbaEnvironment();
    }
}
