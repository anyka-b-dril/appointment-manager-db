//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
package com.sparks.sparklink.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.sparks.sparklink.mapper.AppointmentMapper;
import com.sparks.sparklink.model.Appointment;

public class MongoAppointmentRepo implements AppointmentRepository, AutoCloseable {
	// Initialize memory structure
	private final MongoCollection<Document> collection;
	private final MongoClient mongoClient; 
	
	// Initialize MongoDB connection
	public MongoAppointmentRepo(MongoClient mongoClient, String databaseName) {
		// Initialize client and establish a server connection
		this.mongoClient = mongoClient;
		// Get database; creates automatically if DB does not exist
		MongoDatabase database = mongoClient.getDatabase(databaseName);
		// Connect to collection
		this.collection = database.getCollection("appointments");
		
		// Create index
		this.collection.createIndex(Indexes.ascending("appointmentDate"));
		
		// Verify connection (Ping test)
		database.runCommand(new Document("ping", 1));
	}
	
	// Constructor for testing
	public MongoAppointmentRepo(String connectionString, String databaseName) {
        this(MongoClients.create(connectionString), databaseName);
    }
	
	@Override
	public Appointment save(Appointment appointment) {
		// Convert to BSON
		Document doc = AppointmentMapper.toDocument(appointment);
		// Insert
		collection.insertOne(doc);
		
		return appointment;
	}
	
	@Override
	public Optional<Appointment> findById(String id) {
		Document doc = collection.find(Filters.eq("_id", id)).first();
		
		// If no match was found, return empty
		if (doc == null) {
			return Optional.empty();
		}
		// Convert to Java
		Appointment appointment = AppointmentMapper.toEntity(doc);
		return Optional.of(appointment);
	}
	
	@Override
	public List<Appointment> findAll() {
		List<Appointment> list = new ArrayList<>();
		
		// Add each document found into the list by date ascending
		collection.find()
				  .sort(Sorts.ascending("appointmentDate"))
				  .forEach(doc -> list.add(AppointmentMapper.toEntity(doc)));
		
		return list;
	}
	
	@Override
	public boolean updateById(Appointment appointment) {
		// If object is null, return false
		if (appointment == null || findById(appointment.getID()).isEmpty()) {
			return false;
		}
		// Create replacement document
		// Convert to BSON
		Document doc = AppointmentMapper.toDocument(appointment);
		
		// Replace
		collection.replaceOne(Filters.eq("_id", appointment.getID()), doc, new ReplaceOptions().upsert(true));
		return true;
	}
	
	@Override
	public void deleteById(String id) {
		collection.deleteOne(Filters.eq("_id", id));
	}

	@Override
	public void deleteAll() {
		collection.deleteMany(new Document());
	}

	@Override
	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
		}	
	}
}

