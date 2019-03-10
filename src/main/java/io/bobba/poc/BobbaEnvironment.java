package io.bobba.poc;

import io.bobba.poc.core.Game;
import io.bobba.poc.core.rooms.gamemap.SqState;
import io.bobba.poc.core.rooms.gamemap.pathfinding.DreamPathfinder;
import io.bobba.poc.core.rooms.gamemap.pathfinding.SquarePoint;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;
import org.omg.CORBA.Environment;

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
        Logging.getInstance().writeLine("The environment has initialized successfully. Ready for connections.", LogLevel.Verbose, this.getClass());
    }

    public Game getGame(){
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
                        return;
                    case "cycle":
                        getInstance().getGame().onCycle();
                        Logging.getInstance().writeLine("Cycle forced!", LogLevel.Info, BobbaEnvironment.class);
                        break;
                    case "nextstep":
                        int currentX = Integer.parseInt(commandArgs[1]);
                        int currentY = Integer.parseInt(commandArgs[2]);
                        int targetX = Integer.parseInt(commandArgs[3]);
                        int targetY = Integer.parseInt(commandArgs[4]);

                        SqState[][] sq = new SqState[20][20];
                        for (int i = 0; i < 20; i++) {
                            for (int j = 0; j < 20; j++) {
                                sq[i][j] = SqState.Walkable;
                            }
                        }
                        SquarePoint point = DreamPathfinder.getNextStep(currentX, currentY, targetX, targetY, sq, new double[20][20], 20, 20, false, true);
                        Logging.getInstance().writeLine("NextStep: " + point.getX() + ", " + point.getY(), LogLevel.Info, BobbaEnvironment.class);
                        break;

                    default:
                        Logging.getInstance().writeLine("Invalid command", LogLevel.Info, BobbaEnvironment.class);
                        break;
                }
            }
            catch (Exception e) {
                Logging.getInstance().writeLine("Error with command: " + e.toString() , LogLevel.Info, BobbaEnvironment.class);
            }
        }
    }
}
