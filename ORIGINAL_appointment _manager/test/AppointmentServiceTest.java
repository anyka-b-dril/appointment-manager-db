//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/14/2026
//===============================================================================
package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import main.Appointment;
import main.AppointmentService;
import java.util.Date;
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
    	// get current date and time
    	futureDate = new Date();
    	// initialize calendar package
    	Calendar c = Calendar.getInstance();
    	// set calendar to the current date
    	c.setTime(futureDate);
    	// use calendar to add one day 
    	c.add(Calendar.DATE, 1);
    	// set futureDate to the added calendar date
    	futureDate = c.getTime();
    	
    	// Create empty service object
    	service = new AppointmentService();  	
    }
    
    /*
     *  Appointment Add Appointment Test
     */
    
    // Success: Create appointment and verify it is added to the Map.
    @Test
    @Order(1)
    void testSuccessAddAppointment() {
        service.addAppointment(futureDate, "This is a valid description.");
        
        // Since IDs are random, verify it was added to the list
        assertFalse(service.getAppointmentList().isEmpty());
        assertEquals(1, service.getAppointmentList().size());
        
        // Get Appointment ID
        Appointment appointment = service.getAppointmentList().values().iterator().next();
        String id = appointment.getAppointmentID();
        // Validate correct data was added
        assertTrue(service.getAppointmentList().containsKey(id));
		assertEquals(futureDate, service.getAppointmentList().get(id).getAppointmentDate());
		assertEquals("This is a valid description.", service.getAppointmentList().get(id).getAppointmentDescription()); 
    }
    
    // Success: Verify the generated ID is exactly 10 characters (check padding).
    @Test
    @Order(2)
    void testSuccessAppointmentIDFormat() {
    	 service.addAppointment(futureDate, "Test description.");
    	// Get appointment ID
         Appointment appointment = service.getAppointmentList().values().iterator().next();
         String id = appointment.getAppointmentID();
         
         assertEquals(10, id.length(), "Generated ID must be exactly 10 characters long.");
         assertTrue(id.matches("\\d{10}"), "Generated ID should be a 10-digit numeric string.");
    }
    
    // Fail: addAppointment with Date < past and Date = null.
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
    
    @Test
    @Order(4)
    void testFailAddAppointmentDateNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addAppointment(null, "Test description.");
    	});
    }
    
    // Fail: addAppointment with Description > 50 chars null Description.
    @Test
    @Order(5)
    void testFailAddAppointmentDescTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addAppointment(futureDate, "You should know that this description is wayyy too long for this sorta program...");
    	});
    }
    
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
    	assertEquals(iteration, service.getAppointmentList().size(), "The appointment list size should equal the iteration nummber");
    }

    
    /*
     *  Appointment Delete Test
     */
    
    // Success: Delete a appointment by a known ID and verify it is removed from the Map.
    @Test
    @Order(8)
    void testSuccessDeleteAppointment() {
    	// Get ID 
    	String id = service.addAppointment(futureDate, "This is a valid description.");
        // Delete appointment
        service.deleteAppointment(id);
        
        // Assert the map is now empty
        assertTrue(service.getAppointmentList().isEmpty());
        // Verify ID was deleted
        assertFalse(service.getAppointmentList().containsKey(id));
    }
    
    // Success: Delete a appointment by a know ID with multiple appointments in the List
    @Test
    @Order(9)
    void testDeleteMultipleAppointment() {
    	// Create two appointments and store returned IDs
    	String idDelete = service.addAppointment(futureDate, "This appointment will be deleted.");
        String idKeep = service.addAppointment(futureDate, "This appointment will stay.");
        
        // Delete first appointment
        service.deleteAppointment(idDelete);
        
        // Verify Appointment to Delete does not exist
        assertFalse(service.getAppointmentList().containsKey(idDelete));
        
        // Verify Appointment to Keep still exists
        assertTrue(service.getAppointmentList().containsKey(idKeep));
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
}
