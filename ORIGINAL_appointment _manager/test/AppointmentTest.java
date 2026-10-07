//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/14/2026
//===============================================================================
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.Appointment;

import java.util.Date;
import java.util.Calendar;

class AppointmentTest {
	
	// Each appointment will use a date. 
	// Since before(new Date()) checks the current date AND TIME to the millisecond in the instant it is ran, it is necessary to set the date to a future date.
	// Source: https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
	private Date futureDate;
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
    }
	
	/*
	 *  Object Tests
	 */
	
	// Successful object creation (all valid fields) and object state matches constructor inputs
	@Test
	void testAppointmentClassSuccess() {
	    // Create a valid appointment using the limits: ID(10), Date, Desc(50)
	    Appointment appointment = new Appointment("1234567890", futureDate, "Init schema setup for the appointment service.");
	    
	    // Verify that the object was created and the data is stored correctly
	    assertAll("Appointment Constructor Validation",
	        () -> assertEquals("1234567890", appointment.getAppointmentID()),
	        () -> assertEquals(futureDate, appointment.getAppointmentDate()),
	        () -> assertEquals("Init schema setup for the appointment service.", appointment.getAppointmentDescription())
	    );
	}
	
	/*
	 *  Appointment ID Tests
	 */
	
	// Success: Exactly 10 characters
	@Test
	void testSuccessAppointmentIDExactlyTenCharacters() {
	    Appointment appointment = assertDoesNotThrow(() -> new Appointment("1234567890", futureDate, "Init schema setup for the appointment service."),"Constructor should not throw exception for 10 character ID.");
	    
	    // Verify the state was correctly assigned
	    assertEquals("1234567890", appointment.getAppointmentID(), "Appointment ID should match the 10-character input.");
	}
	
	// Fail: 11 characters
	@Test
	void testFailCreationIdTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("11234567890", futureDate, "Init schema setup for the appointment service.");
		}); 
	}
	
	// Fail: Null
	@Test
	void testFailCreationIdNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(null, futureDate, "Init schema setup for the appointment service.");
		}); 
	}
	
	// Verify: No setter method exists
	@Test
	void testSuccessNoSetIdMethodExists() {
	    try {
	        Appointment.class.getMethod("setAppointmentID", String.class);
	        fail("An 'setAppointmentID' method was found, but the ID should not be updatable.");
	    } catch (NoSuchMethodException e) {
	        // Success: The method does not exist
	    }
	}
	
	/*
	 *  Appointment Date Tests
	 */
	
	// Success: Future date
	@Test
	void testSuccessAppointmentFutureDate() {
	    Appointment appointment = assertDoesNotThrow(() -> new Appointment("1234567890", futureDate, "Init schema setup for the appointment service."),"Constructor should not throw exception for future date.");
	    
	    // Verify the state was correctly assigned
	    assertEquals(futureDate, appointment.getAppointmentDate(), "Appointment Date should match tomorrow's date: " + futureDate);
	}
	
	// Fail: Past date
	@Test
	void testFailAppointmentDatePast() {
		// create past date
		Calendar c = Calendar.getInstance();
		// use calendar to go back two days
		c.add(Calendar.DATE, -2);
		// set futureDate to the added calendar date
		Date pastDate = c.getTime(); 
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("1234567890", pastDate, "Init schema setup for the appointment service.");
		}); 
	}
		
	// Fail: Null
	@Test
	void testFailAppointmentDateNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("1234567890", null, "Init schema setup for the appointment service.");
		}); 
	}
	
	// Success: setAppointmentDate with valid date
	@Test
	void testSuccessAppointmentSetDate() {
		// Create new date
		Calendar c = Calendar.getInstance();
		// Use calendar to go forward three days
		c.add(Calendar.DATE, 3);
		// Set newDate to the added calendar date
		Date newDate = c.getTime();
		
	    // Create a valid appointment using the limits: ID(10), Date, Desc(50)
		Appointment appointment = new Appointment("1234567890", futureDate, "Init schema setup for the appointment service.");
	    appointment.setAppointmentDate(newDate);
	    
	    // Verify that the object was created and the data is stored correctly
	    assertAll("Appointment Constructor Validation",
	        () -> assertEquals("1234567890", appointment.getAppointmentID()),
	        () -> assertEquals(newDate, appointment.getAppointmentDate(), "Appointment date should be " + newDate),
	        () -> assertEquals("Init schema setup for the appointment service.", appointment.getAppointmentDescription())
	    );
	}
	
	// Fail: setAppointmentDate with invalid date (null or past current date/time)
	@Test
	void testFailsetAppointmentPastDate() {
		// Create past date
		Calendar c = Calendar.getInstance();
		// Use calendar to go back two days
		c.add(Calendar.DATE, -2);
		// Set futureDate to the added calendar date
		Date pastDate = c.getTime(); 
				
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// Create initial appointment with a valid date
			Appointment appointment = new Appointment("1234567890", futureDate, "Init schema setup for the appointment service.");
			// Attempt to update the appointment with a prior date
			appointment.setAppointmentDate(pastDate);
		});
	}
	
	@Test
	void testFailsetAppointmentDateNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			// Create initial appointment with a valid date
			Appointment appointment = new Appointment("1234567890", futureDate, "Init schema setup for the appointment service.");
			// Attempt to update the appointment with null
			appointment.setAppointmentDate(null);
		});
	}
	
	/*
	 *  Appointment Description Tests
	 */
	
	// Success: Exactly 50 characters
	@Test
	void testSuccessAppointmentDescExactlyFiftyCharacters() {
	    Appointment appointment = assertDoesNotThrow(() -> new Appointment("1234567890", futureDate, "This description is exactly fifty characters long!"),"Constructor should not throw exception for 50 character Description.");
	    
	    // Verify the state was correctly assigned
	    assertEquals("This description is exactly fifty characters long!", appointment.getAppointmentDescription(), "Appointment Description should match the 50-character input.");
	}
	
	// Fail: 51 characters
	@Test
	void testFailAppointmentDescTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("1234567890", futureDate, "This description was exactly fifty characters long!");
		}); 
	}
			
	// Fail: Null
	@Test
	void testFailAppointmentDescNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("1234567890", futureDate, null);
		});
	}
		
	// Success: setAppointmentDescription with valid string
	@Test
	void testSuccessAppointmentSetDesc() {
	    // Create a valid appointment using the limits: ID(10), past < Date, Desc(50)
	    Appointment appointment = new Appointment("1234567890", futureDate, "Patient is worried about post-surgery leg swelling");
	    appointment.setAppointmentDescription("Patient is worried about post-surgery arm swelling");
	    
	    // Verify that the object was created and the data is stored correctly
	    assertAll("Appointment Constructor Validation",
	        () -> assertEquals("1234567890", appointment.getAppointmentID()),
	        () -> assertEquals(futureDate, appointment.getAppointmentDate()),
	        () -> assertEquals("Patient is worried about post-surgery arm swelling", appointment.getAppointmentDescription(), "Appointment Description should include arm swelling")
	    );
	}
		
	// Fail: setAppointmentDescription with invalid string (null or > 50 chars)
	@Test
	void testFailSetAppointmentDescTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Appointment appointment = new Appointment("1234567890", futureDate, "Patient is worried about post-surgery leg swelling");
			appointment.setAppointmentDescription("The patient is worried that their entire arm may fall off.");
		});
	}
	
	@Test
	void testFailSetAppointmentDescNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Appointment appointment = new Appointment("1234567890", futureDate, "Patient is worried about post-surgery leg swelling");
			appointment.setAppointmentDescription(null);
		});
	}

}
