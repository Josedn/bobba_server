package io.bobba.poc.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	public DatabaseManager(String host, int port, String databaseName) throws Exception {
		this.mongoClient = new MongoClient(host, port);
		this.database = mongoClient.getDatabase(databaseName);
	}

	public MongoDatabase getDatabase() {
		return database;
	}
}
