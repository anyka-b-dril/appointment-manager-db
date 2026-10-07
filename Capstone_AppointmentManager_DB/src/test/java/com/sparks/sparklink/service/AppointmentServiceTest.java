//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
package com.sparks.sparklink.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import com.sparks.sparklink.model.Appointment;
import com.sparks.sparklink.repository.AppointmentRepository;
import com.sparks.sparklink.repository.InMemoryAppointmentRepo;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppointmentServiceTest {
	// Each appointment will use a date. 
	// Since before(new Date()) checks the current date AND TIME to the millisecond in the instant it is ran, it is necessary to set the date to a future date.
	// Source: https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
	private Date futureDate;
	private AppointmentService service;

    @BeforeEach
    void setUp() {
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
    	
    	// Create empty service object
    	AppointmentRepository repo = new InMemoryAppointmentRepo();
    	service = new AppointmentService(repo);  	
    }
    
    /*
     *  Appointment Add Appointment Test
     */
    
    // Success: Create appointment and verify it is added to the Map.
    @Test
    @Order(1)
    void testSuccessAddAppointment() {
    	// Create an appointment
        service.addAppointment(futureDate, "This is a valid description.");
        
        // Since IDs are random, verify an appointment was added to the list
        assertEquals(1, service.getAllAppointments().size());
        
        // Get Appointment ID
        List<Appointment> appointment = service.getAllAppointments();
        String id = appointment.get(0).getID();
        
        // Validate correct data was added
		assertEquals(id, service.findAppointment(id).get().getID());
		assertEquals(futureDate, service.findAppointment(id).get().getDate());
		assertEquals("This is a valid description.", service.findAppointment(id).get().getDescription()); 
    }
    
    // Success: Verify the generated ID is exactly 10 characters (check padding).
    @Test
    @Order(2)
    void testSuccessAppointmentIDFormat() {
    	 //Create an appointment
    	 service.addAppointment(futureDate, "Test description.");
    	 
    	 // Get appointment ID
    	 List<Appointment> appointment = service.getAllAppointments();
         String id = appointment.get(0).getID();
         
         assertEquals(10, id.length(), "Generated ID must be exactly 10 characters long.");
         assertTrue(id.matches("\\d{10}"), "Generated ID should be a 10-digit numeric string.");
    }
    
    // Fail: addAppointment with Date < past.
    @Test
    @Order(3)
    void testFailAddAppointmentDatePast() {
    	// create past date
    	Calendar c = Calendar.getInstance();
    	// use calendar to go back two days
    	c.add(Calendar.DATE, -2);
    	// set futureDate to the added calendar date
    	Date pastDate = c.getTime();
    	
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addAppointment(pastDate, "Test description.");
    	});
    }
    
    // Fail: add appointment with Date = null.
    @Test
    @Order(4)
    void testFailAddAppointmentDateNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addAppointment(null, "Test description.");
    	});
    }
    
    // Fail: add appointment with Description > 50 chars.
    @Test
    @Order(5)
    void testFailAddAppointmentDescTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addAppointment(futureDate, "You should know that this description is wayyy too long for this sorta program...");
    	});
    }
    
    // Fail: add appointment with null Description.
    @Test
    @Order(6)
    void testFailAddAppointmentDescNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addAppointment(futureDate, null);
    	});
    }

    /*
     *  Appointment Unique ID Test
     */
    
    // Success: Add a high volume of appointments (e.g., 100) and verify the Map size is exactly 100.
    // 			Verify that the do-while loop prevents duplicate IDs from overwriting data.
    @Test
    @Order(7)
    void testIdUniqueness() {
    	int iteration = 100; //For 100 new appointments
    	
    	// Create 100 appointments
    	for (int i = 0; i < iteration; i++) {
    		service.addAppointment(futureDate, "Generic appointment description.");
    	}
    	// If all IDs are unique, the size should equal the set iteration number
    	// else, some IDs are being overwritten :(((
    	assertEquals(iteration, service.getAllAppointments().size(), "The appointment list size should equal the iteration nummber");
    }

    
    /*
     *  Appointment Delete Test
     */
    
    // Success: Delete a appointment by a known ID and verify it is removed from the Map.
    @Test
    @Order(8)
    void testSuccessDeleteAppointment() {
    	// Create an appointment
    	Appointment created = service.addAppointment(futureDate, "Generic appointment description.");
    	// Get ID
    	String id = created.getID();
        
        // Delete appointment
        service.deleteAppointment(id);
        
        // Assert the map is now empty
        assertTrue(service.getAllAppointments().isEmpty());
        // Verify ID was deleted
        assertTrue(service.findAppointment(id).isEmpty());
    }
    
    // Success: Delete a appointment by a know ID with multiple appointments in the List
    @Test
    @Order(9)
    void testDeleteMultipleAppointment() {
    	// Create two appointments and store returned IDs
    	Appointment delete = service.addAppointment(futureDate, "This appointment will be deleted.");
    	String idDelete = delete.getID();
    	
    	Appointment keep = service.addAppointment(futureDate, "This appointment will stay.");
    	String idKeep = keep.getID();
        
        // Delete first appointment
        service.deleteAppointment(idDelete);
        
        // Verify Appointment to Delete does not exist
        assertTrue(service.findAppointment(idDelete).isEmpty());
        
        // Verify Appointment to Keep still exists
        assertTrue(service.findAppointment(idKeep).isPresent());
    }
    
    // Fail: Attempt to delete an ID that does not exist (Verify IllegalArgumentException).
    @Test
    @Order(10)
    void testFailDeleteAppointmentUnknownID() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.deleteAppointment("1234567890");
    	});
    	
    }
    
    // Fail: Attempt to delete an ID that is null (Verify IllegalArgumentException).
    @Test
    @Order(11)
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
    @Order(12)
    void testSuccessUpdateAppointment() {
    	// Create an appointment
    	Appointment app = service.addAppointment(futureDate, "This is a valid description.");
        // Get ID
        String id = app.getID();
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
        // Get appointment from list
        Appointment retrieved = service.findAppointment(app.getID()).get();
        // Verify new date and description
        assertEquals(newDate, retrieved.getDate());
        assertEquals("This is a new description.", retrieved.getDescription());   	
    }
    
    // Success: Update an appointment date by known ID and verify new date with existing description.
    @Test
    @Order(13)
    void testSuccessUpdateAppointmentDate() {
    	// Create an appointment
    	Appointment app = service.addAppointment(futureDate, "This is a valid description.");
        // Get ID
        String id = app.getID();
        // Get new date
    	Calendar c = Calendar.getInstance();
    	// use calendar to go forward two days
    	c.add(Calendar.DATE, 2);
    	// set futureDate to the added calendar date
    	Date newDate = c.getTime();
    	
        // Update appointment
        boolean updated = service.updateAppointment(id, newDate, null);
        
        // Verify appointment was updated
        assertTrue(updated);
        // Get appointment from list
        Appointment retrieved = service.findAppointment(app.getID()).get();
        // Verify new date and existing description
        assertEquals(newDate, retrieved.getDate());
        assertEquals("This is a valid description.", retrieved.getDescription());   	
    }
    
    // Success: Update an appointment date by known ID and verify new description with existing date.
    @Test
    @Order(14)
    void testSuccessUpdateAppointmentDesc() {
    	// Create an appointment
    	Appointment app = service.addAppointment(futureDate, "This is a valid description.");
        // Get ID
        String id = app.getID();
    	
        // Update appointment
        boolean updated = service.updateAppointment(id, null, "This is a new description.");
        
        // Verify appointment was updated
        assertTrue(updated);
        // Get appointment from list
        Appointment retrieved = service.findAppointment(app.getID()).get();
        // Verify new date and description
        assertEquals(futureDate, retrieved.getDate());
        assertEquals("This is a new description.", retrieved.getDescription());   	
    }

    // Fail: Attempt to update an appointment by ID that does not exist.
    @Test
    @Order(15)
    void testFailUpdateAppointmentUnknownID() {
    	// Create an appointment
    	Appointment app = service.addAppointment(futureDate, "This is a valid description.");        
    	
        // Update appointment
        boolean updated = service.updateAppointment("1234567890", futureDate, "This is a random new description.");
        // Verify appointment was NOT updated
        assertFalse(updated);
    }
    
    // Fail: Attempt to update an ID that is null.
    @Test
    @Order(16)
    void testFailUpdateAppointmentNullID() {
    	// Create an appointment
    	service.addAppointment(futureDate, "This is a valid description.");
        
        // Update appointment
        boolean updated = service.updateAppointment(null, futureDate, "This is a random new description.");
        // Verify appointment was NOT updated
        assertFalse(updated);
    	
    }
    
    // Fail: Attempt to update an appointment by known ID with past date
    @Test
    @Order(17)
    void testFailUpdateAppointmentPastDate() {
    	// Create past date
    	Calendar c = Calendar.getInstance();
    	// Use calendar to go back two days
    	c.add(Calendar.DATE, -2);
    	// Set futureDate to the added calendar date
    	Date pastDate = c.getTime();
    	    	
    	// Create an appointment
    	Appointment app = service.addAppointment(futureDate, "This is a valid description.");
        // Get ID
        String id = app.getID();
        
        // Update appointment and verify exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	service.updateAppointment(id, pastDate, "This is a valid description.");
        });    	
    }
    
    // Fail: Attempt to update an appointment by known ID with too long description
    @Test
    @Order(18)
    void testFailUpdateAppointmentDescTooLong() {
    	// Create an appointment
    	Appointment app = service.addAppointment(futureDate, "This is a valid description.");
        // Get ID
        String id = app.getID();
        
        // Update appointment and verify exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
        	service.updateAppointment(id, futureDate, "This is a random new description that is just wayyyyy tooo long.");
        });    	
    }
}
