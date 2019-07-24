package io.bobba.poc.database;

import com.zaxxer.hikari.HikariDataSource;

import io.bobba.poc.misc.logging.LogLevel;
import io.bobba.poc.misc.logging.Logging;

public class Database {

    private HikariDataSource dataSource;


    private DatabasePool databasePool;

    public Database(int poolMaxSize, int poolMinSize, String host, int port, String database, String username, String password) throws Exception {
        this.databasePool = new DatabasePool();
        this.databasePool.setStoragePooling(poolMaxSize, poolMinSize, host, port, database, username, password);            
        this.dataSource = this.databasePool.getDatabase();
        Logging.getInstance().writeLine("Connected to db", LogLevel.Verbose, this.getClass());
    }

    public void dispose() {
        if (this.databasePool != null) {
            this.databasePool.getDatabase().close();
        }

        this.dataSource.close();
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }

    public DatabasePool getDatabasePool() {
        return this.databasePool;
    }
}

