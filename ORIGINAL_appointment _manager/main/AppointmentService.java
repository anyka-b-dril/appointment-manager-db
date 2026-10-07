//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/14/2026
//===============================================================================
package main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AppointmentService {
	// Appointment list with ID as the key
	private Map<String, Appointment> appointmentList = new HashMap<>();
	// Initialize random integer for unique Appointment ID
	private final Random random = new Random();
	
	/*
	 *  Add Appointment with a unique ID
	 */
	public String addAppointment(Date appointmentDate, String appointmentDescription) {
		int rawAppointmentID;
		String appointmentID;
		
		// Validate current random generated id is not in use
		// Otherwise, we risk overwriting data
		do {
			rawAppointmentID = random.nextInt(Integer.MAX_VALUE); // Generate a number between 0-2.1 billion (all are 10 digits or fewer)
			// Format it to be exactly 10 digits with leading zeros
			appointmentID = String.format("%010d", rawAppointmentID);
		} while (appointmentList.containsKey(appointmentID));
		
		// Create new Appointment object
		Appointment newAppointment = new Appointment(appointmentID, appointmentDate, appointmentDescription);
		// Add Appointment to Appointment list
		appointmentList.put(appointmentID, newAppointment);
				
		// Celebrate success
		System.out.println("Created new Appointment: ID: " + appointmentID + ", Date: " + appointmentDate + ", Description: " + appointmentDescription);
		
		// Return the Appointment ID so the application knows what was created
		return appointmentID;
	}
	
	/*
	 *  Delete Appointment by unique ID
	 */
	public void deleteAppointment(String appointmentID) {
		if (appointmentList.containsKey(appointmentID)) {
			// Record deleted items
			Appointment removed = appointmentList.remove(appointmentID);
			// Report deletion
			System.out.println("Deleted Appointment: ID: " + removed.getAppointmentID() + ", Date: " + removed.getAppointmentDate());
		}
		else {
			// Report error
			throw new IllegalArgumentException("Error: ID " + appointmentID + " not found.");
		}
		
	}
		
	/*
	 *  Get contents
	 */
	public Map<String, Appointment> getAppointmentList() {
	    return appointmentList;
	}
}
