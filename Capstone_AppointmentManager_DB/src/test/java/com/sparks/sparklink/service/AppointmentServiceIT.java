//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
package com.sparks.sparklink.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import com.sparks.sparklink.config.DatabaseConfig;
import com.sparks.sparklink.model.Appointment;
import com.sparks.sparklink.repository.AppointmentRepository;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentServiceIT {
	// Each appointment will use a date. 
	// Since before(new Date()) checks the current date AND TIME to the millisecond in the instant it is ran, it is necessary to set the date to a future date.
	// Source: https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
	private static Date futureDate;
	private static final String TEST_URI = "mongodb://localhost:27017";
	private static final String TEST_DB = "appointment_test";
	private static AppointmentRepository mongoRepo;
	private static AppointmentService service;

	@BeforeAll
	static void setUp() {
	  	// Get current date and time
	   	futureDate = new Date();
	   	// Initialize calendar package
	   	Calendar c = Calendar.getInstance();
	   	// Set calendar to the current date
	   	c.setTime(futureDate);
	   	// Use calendar to add one day 
	   	c.add(Calendar.DATE, 1);
	   	// Set futureDate to the added calendar date
	   	futureDate = c.getTime();
	   	
	   	// Create live Mongo service object
	   	mongoRepo = DatabaseConfig.createMongoRepo(TEST_URI, TEST_DB);
	    // Inject the live MongoDB repo into the service layer
        service = new AppointmentService(mongoRepo);
        
        // Clear any leftover data before running each test
        mongoRepo.deleteAll(); 	
	   }
	
	@AfterEach
	void cleanUp() {
		// Clear any leftover data after running each test
        mongoRepo.deleteAll(); 
	}
	
	@AfterAll
	static void tearDown() {
		if (mongoRepo != null) {
			try {
				mongoRepo.close();
			}
			catch (Exception e) {
				System.out.println("Error closing MongoDB: " + e);
			}
		}
	}
	    
	 /*
	  *  Appointment Add Appointment Test
	  */
	    
	 // Success: Create appointment and verify it is added to the Map.
	 @Test
	 @Order(1)
	 void testSuccessAddAppointment() {
		 Appointment created = service.addAppointment(futureDate, "This is a valid description.");
		   
		 // Validate correct data was added
		 assertTrue(service.findAppointment(created.getID()).isPresent());
		 assertEquals(futureDate, created.getDate());
		 assertEquals("This is a valid description.", created.getDescription());
	  }
	   
	  // Success: Verify the generated ID is exactly 10 characters (check padding).
	  @Test
	  @Order(2)
	  void testSuccessAppointmentIDFormat() {
		  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
		  // Get ID
		  String id = created.getID();
		  
		  // Validate ID length
		  assertEquals(10, id.length(), "Generated ID must be exactly 10 characters long.");
		  assertTrue(id.matches("\\d{10}"), "Generated ID should be a 10-digit numeric string.");
	  }
	  
	  // Success: Create appointments and verify all are added to the Map.
	  @Test
	  @Order(3)
	  void testSuccessAddMultipleAppointments() {
		  service.addAppointment(futureDate, "Appointment #1.");
		  service.addAppointment(futureDate, "Appointment #2.");
		  service.addAppointment(futureDate, "Appointment #3.");
			   
		  // Validate correct data was added
		  assertTrue(service.getAllAppointments().size() == 3);
	  }
	  
	  // Fail: add appointment with Date < past.
	  @Test
	  @Order(4)
	  void testFailAddAppointmentDatePast() {
		// Create past date
	    Calendar c = Calendar.getInstance();
	    // Use calendar to go back two days
	    c.add(Calendar.DATE, -2);
	    // Set futureDate to the added calendar date
	    Date pastDate = c.getTime();
	    
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addAppointment(pastDate, "Test description.");
    	});
	  }
	  
	  // Fail: add appointment with Date = null.
	  @Test
	  @Order(5)
	  void testFailAddAppointmentDateNull() {
		  Assertions.assertThrows(IllegalArgumentException.class, () -> {
			  service.addAppointment(null, "Test description.");
	      });
	    }
	  
	  // Fail: add appointment with Description > 50 chars.
	  @Test
	  @Order(6)
	  void testFailAddAppointmentDescTooLong() {
		  Assertions.assertThrows(IllegalArgumentException.class, () -> {
	      service.addAppointment(futureDate, "You should know that this description is wayyy too long for this sorta program...");
	      });
	  }
	   
	  // Fail: add appointment with null Description.
	  @Test
	  @Order(7)
	  void testFailAddAppointmentDescNull() {
		  Assertions.assertThrows(IllegalArgumentException.class, () -> {
	      service.addAppointment(futureDate, null);
	      });
	  }

	  /*
	   *  Appointment Unique ID Test
	   */
	  
	  // Success: Add a high volume of appointments (e.g., 100) and verify the Map size is exactly 100.
	  // 	      Verify that the do-while loop prevents duplicate IDs from overwriting data.
	  @Test
	  @Order(8)
	  void testIdUniqueness() {
	  	int iteration = 100; //For 100 new appointments
	  	List<String> ids = new ArrayList<String>();
	    	
	   	// Create 100 appointments
	   	for (int i = 0; i < iteration; i++) {
	   		Appointment created = service.addAppointment(futureDate, "Generic appointment description.");
	   		// Store IDs (to be cleanly deleted)
	   		ids.add(created.getID());
	   	}
	   	
	   	// Validate that each ID is present in the DB
	   	for (int i = 0; i < ids.size(); i++) {
	   		assertTrue(service.findAppointment(ids.get(i)).isPresent(), "Appointment " + ids.get(i) + " is missing");
	   	}
	  }
	  
	  /*
	   *  Appointment Delete Test
	   */
	  
	  // Success: Delete a appointment by a known ID and verify it is removed from the Map.
	  @Test
	  @Order(9)
	  void testSuccessDeleteAppointment() {
		  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
		  String id = created.getID();
		  
		  // Validate appointment was added
		  assertTrue(service.findAppointment(id).isPresent(),"Appointment " + id + " is missing");
		  
		  service.deleteAppointment(id);
		  
		  // Validate appointment was deleted
		  assertFalse(service.findAppointment(id).isPresent());
	  }
	  
	  // Success: Delete a appointment by a know ID with multiple appointments in the List
	  @Test
	  @Order(10)
	  void testDeleteMultipleAppointment() {
		  // Create two appointments and store returned IDs
		  Appointment toBeDeleted = service.addAppointment(futureDate, "This appointment will be deleted.");
		  String idDelete = toBeDeleted.getID();
		  
		  Appointment toBeKept = service.addAppointment(futureDate, "This appointment will stay.");
		  String idKeep = toBeKept.getID();
		  
		  // Delete first appointment
		  service.deleteAppointment(idDelete);
		  
		  // Verify appointment toBeDeleted does not exist
		  assertFalse(service.findAppointment(idDelete).isPresent(),"Appointment " + idDelete + " still exists");
		  
		  // Verify appointment toBeKept still exists
		  assertTrue(service.findAppointment(idKeep).isPresent(),"Appointment " + idKeep + " is missing");
	  }
	  
	  // Fail: Attempt to delete an ID that does not exist (Verify IllegalArgumentException).
	  @Test
	  @Order(11)
	  void testFailDeleteAppointmentUnknownID() {
		  Assertions.assertThrows(IllegalArgumentException.class, () -> {
	      service.deleteAppointment("1234567890");
	      });
	  }
	    
	  // Fail: Attempt to delete an ID that is null (Verify IllegalArgumentException).
	  @Test
	  @Order(12)
	  void testFailDeleteAppointmentNullID() {
		  Assertions.assertThrows(IllegalArgumentException.class, () -> {
		  service.deleteAppointment(null);
		  });
	  }

	  /*
	   *  Appointment Update Test
	   */
	    
	  // Success: Update an appointment by known ID and verify new contents.
	  @Test
	  @Order(13)
	  void testSuccessUpdateAppointment() {
		  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
		  String id = created.getID();
		  
	      // Get new date
	      Calendar c = Calendar.getInstance();
	      // Use calendar to go forward two days
	      c.add(Calendar.DATE, 2);
	      // Set futureDate to the added calendar date
	      Date newDate = c.getTime();
	      
	      // Update appointment
	      boolean updated = service.updateAppointment(id, newDate, "This is a new description.");
	        
	      // Verify appointment was updated
	      assertTrue(updated);
	      
	      // Get updated appointment from db
	      Appointment updatedApp = service.findAppointment(id).get();
	      
	      // Verify new date and description
	      assertEquals(newDate, updatedApp.getDate());
	      assertEquals("This is a new description.", updatedApp.getDescription());
	  }
	  
	  // Success: Update an appointment date by known ID and verify new date with existing description.
	  @Test
	  @Order(14)
	  void testSuccessUpdateAppointmentDate() {
		  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
		  String id = created.getID();
		  
	      // Get new date
	      Calendar c = Calendar.getInstance();
	      // Use calendar to go forward two days
	      c.add(Calendar.DATE, 2);
	      // Set futureDate to the added calendar date
	      Date newDate = c.getTime();
	      
	      // Update appointment
	      boolean updated = service.updateAppointment(id, newDate, null);
	      
	      // Verify appointment was updated
	      assertTrue(updated);
	      
	      // Get updated appointment from db
	      Appointment updatedApp = service.findAppointment(id).get();
	      
	      // Verify new date and description
	      assertEquals(newDate, updatedApp.getDate());
	      assertEquals("This is a valid description.", updatedApp.getDescription());
	  }
	  
	  // Success: Update an appointment date by known ID and verify new description with existing date.
	  @Test
	  @Order(15)
	  void testSuccessUpdateAppointmentDesc() {
		  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
		  String id = created.getID();
		  
	      // Update appointment
	      boolean updated = service.updateAppointment(id, null, "This is a new description.");
	      
	      // Verify appointment was updated
	      assertTrue(updated);
	      
	      // Get updated appointment from db
	      Appointment updatedApp = service.findAppointment(id).get();
	      
	      // Verify new date and description
	      assertEquals(futureDate, updatedApp.getDate());
	      assertEquals("This is a new description.", updatedApp.getDescription());
	  }
	  
	  // Fail: Attempt to update an appointment by ID that does not exist.
	  @Test
	  @Order(16)
	  void testFailUpdateAppointmentUnknownID() {
		  // Create an appointment
	  	  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
	  	  String id = created.getID();
	  	
	      // Update appointment
	      boolean updated = service.updateAppointment("1234567890", futureDate, "This is a random new description.");
	      // Verify appointment was NOT updated
	      assertFalse(updated);
	  }

	  // Fail: Attempt to update an ID that is null.
	  @Test
	  @Order(17)
	  void testFailUpdateAppointmentNullID() {
		  // Create an appointment
		  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
		  String id = created.getID();
	      
	      // Update appointment
	      boolean updated = service.updateAppointment(null, futureDate, "This is a random new description.");
	      // Verify appointment was NOT updated
	      assertFalse(updated);
	  }

	  // Fail: Attempt to update an appointment by known ID with past date
	  @Test
	  @Order(18)
	  void testFailUpdateAppointmentPastDate() {
		  // Create past date
	  	  Calendar c = Calendar.getInstance();
	  	  // Use calendar to go back two days
	  	  c.add(Calendar.DATE, -2);
	  	  // Set futureDate to the added calendar date
	  	  Date pastDate = c.getTime();
	  	    	
	  	  // Create an appointment
	  	  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
	      String id = created.getID();
	      
	      // Update appointment and verify exception
	      Assertions.assertThrows(IllegalArgumentException.class, () -> {
	      	service.updateAppointment(id, pastDate, "This is a valid description.");
	      });
	  }

	  // Fail: Attempt to update an appointment by known ID with too long description
	  @Test
	  @Order(19)
	  void testFailUpdateAppointmentDescTooLong() {
		  // Create an appointment
	  	  Appointment created = service.addAppointment(futureDate, "This is a valid description.");
	      String id = created.getID();
	      
	      // Update appointment and verify exception
	      Assertions.assertThrows(IllegalArgumentException.class, () -> {
	      	service.updateAppointment(id, futureDate, "This is a random new description that is just wayyyyy tooo long.");
	      });
	  }
}
