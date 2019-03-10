package io.bobba.poc.core;

import io.bobba.poc.BobbaEnvironment;
import io.bobba.poc.core.gameclients.GameClientManager;
import io.bobba.poc.core.items.BaseItemManager;
import io.bobba.poc.core.rooms.Room;
import io.bobba.poc.core.rooms.gamemap.RoomModel;
import io.bobba.poc.core.users.Authenticator;
import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;
import io.bobba.poc.net.ConnectionManager;

import java.time.Duration;
import java.time.Instant;

public class Game {
    private ConnectionManager connectionManager;
    private GameClientManager gameClientManager;
    private Authenticator authenticator;
    private BaseItemManager itemManager;
    private Room room;

    private final int DELTA_TIME = 500;

    public Game() {
        this.gameClientManager = new GameClientManager();
        this.connectionManager = new ConnectionManager(BobbaEnvironment.PORT, this.gameClientManager);
        this.authenticator = new Authenticator();
        this.itemManager = new BaseItemManager();
        this.room = new Room(1, "Test room", new RoomModel());

        Thread roomThread = new Thread(new Runnable() {
            @Override
            public void run() {
                gameThread();
            }
        });
        roomThread.start();
    }

    private void gameThread() {
        Instant starts, ends;
        while (true) {
            try {
                starts = Instant.now();
                onCycle();
                ends = Instant.now();

                long sleepTime = DELTA_TIME - Duration.between(starts, ends).toMillis();
                if (sleepTime < 0)
                {
                    sleepTime = 0;
                }

                Thread.sleep(sleepTime);

            } catch (Exception e) {
                Logging.getInstance().writeLine("Game cycle error: " + e.toString(), LogLevel.Warning, this.getClass());
            }
        }
    }

    public void onCycle() {
        room.onCycle();
    }

    public Room getRoom() {
        return room;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public GameClientManager getGameClientManager() {
        return gameClientManager;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public BaseItemManager getItemManager() {
        return itemManager;
    }
}
