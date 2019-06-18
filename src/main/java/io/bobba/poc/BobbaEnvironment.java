package io.bobba.poc;

import io.bobba.poc.core.Game;
import io.bobba.poc.core.rooms.gamemap.SqState;
import io.bobba.poc.misc.configs.ConfigManager;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

import java.util.Scanner;

public class BobbaEnvironment {
    private static BobbaEnvironment instance;
    private static final String VERSION = "1.0.0 alpha";
    private static ConfigManager configManager = new ConfigManager("config.txt");

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

        Logging.getInstance().setLogLevel(Logging.valueOfLogLevel(configManager.getLogLevel()));
        try {
            this.game = new Game(Integer.parseInt(configManager.getPort()));
            Logging.getInstance().writeLine("The environment has initialized successfully. Ready for connections.", LogLevel.Verbose, this.getClass());
        } catch (Exception e) {
            Logging.getInstance().logError("Error initializing server", e, this.getClass());
        }
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public Game getGame() {
        return this.game;
    }

    public static BobbaEnvironment getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new BobbaEnvironment();
        Scanner scn = new Scanner(System.in);
        String command;
        while (true) {
            try {
                command = scn.nextLine();
                String[] commandArgs = command.split(" ");
                switch (commandArgs[0]) {
                    case "exit":
                    case "stop":
                        Logging.getInstance().writeLine("Stopping server...", LogLevel.Info, BobbaEnvironment.class);
                        getInstance().getGame().getConnectionManager().stop();
                        scn.close();
                        System.exit(0);
                        return;
                    case "cycle":
                        getInstance().getGame().onCycle();
                        Logging.getInstance().writeLine("Cycle forced!", LogLevel.Info, BobbaEnvironment.class);
                        break;

                    case "map":
                        SqState[][] map = getInstance().getGame().getRoom().getGameMap().getMap();
                        for (int i = 0; i < getInstance().getGame().getRoom().getGameMap().getRoomModel().maxY; i++) {
                            for (int j = 0; j < getInstance().getGame().getRoom().getGameMap().getRoomModel().maxX; j++) {
                                System.out.print(map[j][i].ordinal() + "\t| ");
                            }
                            System.out.println();
                        }
                        break;

                    case "loglevel":
                        Logging.getInstance().setLogLevel(Logging.valueOfLogLevel(commandArgs[1]));
                        configManager.setLogLevel(commandArgs[1]);
                        Logging.getInstance().writeLine("Log level set to: " + Logging.getInstance().getLogLevel().toString(), LogLevel.Info, BobbaEnvironment.class);
                        break;
                    default:
                        Logging.getInstance().writeLine("Invalid command", LogLevel.Info, BobbaEnvironment.class);
                        break;
                }
            } catch (Exception e) {
                Logging.getInstance().writeLine("Error with command: " + e.toString(), LogLevel.Info, BobbaEnvironment.class);
            }
        }
    }
}
