//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
package com.sparks.sparklink;

import com.mongodb.MongoException;
import com.sparks.sparklink.config.DatabaseConfig;
import com.sparks.sparklink.repository.AppointmentRepository;
import com.sparks.sparklink.service.AppointmentService;
import com.sparks.sparklink.view.AppointmentManager;

public class Main {
	public static void main(String[] args) {
		// Attempt to connect to MongoDB
		try (AppointmentRepository repo = DatabaseConfig.createMongoRepo()){
			
			// Inject repository into service
			AppointmentService service = new AppointmentService(repo);
			
			// Inject service into manager
			AppointmentManager manager = new AppointmentManager(service);
			
			// Start application
			manager.menu();
		}
		catch (MongoException e) {
			System.err.println("Error: Unable to connect to MongoDB.");
			System.err.println("Please ensure your local MongoDB service is running.");
		}
		catch (Exception e) {
			System.err.println("Error: An enexpected error has occured during start up: " + e.getMessage());
		}
	}
}
