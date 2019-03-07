package io.bobba.poc;

import io.bobba.poc.core.Game;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

import java.util.Scanner;

public class BobbaEnvironment {
    private static BobbaEnvironment instance;
    public static final String VERSION = "1.0.0 alpha";
    public static final int PORT = 1232;

    private Game game;

    private BobbaEnvironment() {
        System.out.println("");
        System.out.println("|         |    |          o     ");
        System.out.println("|---.,---.|---.|---.,---. .,---.");
        System.out.println("|   ||   ||   ||   |,---| ||   |");
        System.out.println("`---'`---'`---'`---'`---^o``---'");
        System.out.println(VERSION);
        System.out.println("Copyright (c) 2019 - relevance");
        System.out.println();

        Logging.getInstance().setLogLevel(LogLevel.SuperDebug);
        this.game = new Game();
        Logging.getInstance().writeLine("The environment has initialized successfully. Ready for connections.", LogLevel.Verbose);

        Scanner scn = new Scanner(System.in);
        String command;
        while (true) {
            command = scn.nextLine();
            String[] args = command.split(" ");
            switch (args[0]) {
                case "exit":
                case "stop":
                    Logging.getInstance().writeLine("Stopping server...", LogLevel.Info);
                    return;
                default:
                    Logging.getInstance().writeLine("Invalid command", LogLevel.Info);
                    break;
            }
        }
    }

    public static BobbaEnvironment getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new BobbaEnvironment();
    }
}
