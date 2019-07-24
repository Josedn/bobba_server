package io.bobba.poc.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

class DatabasePool {
	private HikariDataSource database;

	public void setStoragePooling(int poolMaxSize, int poolMinSize, String host, int port, String database,
			String username, String password) {
		HikariConfig databaseConfiguration = new HikariConfig();
		databaseConfiguration.setMaximumPoolSize(poolMaxSize);
		databaseConfiguration.setMinimumIdle(poolMinSize);
		databaseConfiguration.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
		databaseConfiguration.addDataSourceProperty("serverName", host);
		databaseConfiguration.addDataSourceProperty("port", port);
		databaseConfiguration.addDataSourceProperty("databaseName", database);
		databaseConfiguration.addDataSourceProperty("user", username);
		databaseConfiguration.addDataSourceProperty("password", password);
		databaseConfiguration.addDataSourceProperty("dataSource.logger", "com.mysql.jdbc.log.StandardLogger");
		databaseConfiguration.addDataSourceProperty("dataSource.logSlowQueries", "true");
		databaseConfiguration.addDataSourceProperty("dataSource.dumpQueriesOnException", "true");
		databaseConfiguration.addDataSourceProperty("prepStmtCacheSize", "500");
		databaseConfiguration.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		// databaseConfiguration.addDataSourceProperty("dataSource.logWriter",
		// Logging.getErrorsSQLWriter());
		databaseConfiguration.addDataSourceProperty("cachePrepStmts", "true");
		databaseConfiguration.addDataSourceProperty("useServerPrepStmts", "true");
		databaseConfiguration.addDataSourceProperty("rewriteBatchedStatements", "true");
		databaseConfiguration.addDataSourceProperty("useUnicode", "true");
		databaseConfiguration.setAutoCommit(true);
		databaseConfiguration.setConnectionTimeout(300000L);
		databaseConfiguration.setValidationTimeout(5000L);
		databaseConfiguration.setLeakDetectionThreshold(20000L);
		databaseConfiguration.setMaxLifetime(1800000L);
		databaseConfiguration.setIdleTimeout(600000L);
		// databaseConfiguration.setDriverClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		this.database = new HikariDataSource(databaseConfiguration);
	}

	public HikariDataSource getDatabase() {
		return this.database;
	}
}