//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
package com.sparks.sparklink.config;

import java.util.concurrent.TimeUnit;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.sparks.sparklink.repository.AppointmentRepository;
import com.sparks.sparklink.repository.MongoAppointmentRepo;

public class DatabaseConfig {
	// Default connection parameters
	private static final String DEFAULT_DB_URI = "mongodb://localhost:27017";
	private static final String DEFAULT_DB = "appointment";
	
	// Create and initialize the MongoDB repository
	public static AppointmentRepository createMongoRepo() {
		return new MongoAppointmentRepo(DEFAULT_DB_URI, DEFAULT_DB);
	}
	
	// Explicit connection settings
	// Overload method (for testing or external env variables)
	public static AppointmentRepository createMongoRepo(String connectionString, String dbName) {
		// Ref: https://www.mongodb.com/docs/drivers/java/sync/current/connection/mongoclient/
		MongoClientSettings settings = MongoClientSettings.builder()
				// Connection string
				.applyConnectionString(new ConnectionString(connectionString))
				// Socket and timeout settings
				.applyToSocketSettings(builder -> 
						builder.connectTimeout(5, TimeUnit.SECONDS)
							.readTimeout(5, TimeUnit.SECONDS))
				// Cluster and server settings
				.applyToClusterSettings(builder ->
						builder.serverSelectionTimeout(5, TimeUnit.SECONDS))
				.build();
		
		MongoClient mongoClient = MongoClients.create(settings);
		// Pass configuration to the repository
		return new MongoAppointmentRepo(connectionString, dbName);
	}	
}
