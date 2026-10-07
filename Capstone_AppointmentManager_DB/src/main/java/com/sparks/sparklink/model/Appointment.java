//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================

package com.sparks.sparklink.model;

import java.util.Date;

public class Appointment {
	// appointment ID shall not be update-able
	private final String appointmentID;
	private Date appointmentDate;
	private String appointmentDescription;
	
	/*
	 *  Validation Functions 
	 */
	private boolean validateFieldLength(String fieldName, String field, int limit) {
		// Explicit error handling for null and above limit values
		// If the target field is null; return false
		if (field == null) {
			throw new IllegalArgumentException("Error: " + fieldName + " cannot be null.");
		}
		// If the target field is an empty string; return false
		if (field.isEmpty()) {
			throw new IllegalArgumentException("Error: " + fieldName + " cannot be empty.");
		}
		// If the target field is greater than its set character limit; return false
		if (field.length() > limit) {
			throw new IllegalArgumentException("Error: " + fieldName + " is too long.");
		}
		return true;
	}
	
	//  The appointment Date field cannot be in the past. The appointment Date field shall not be null.
	private boolean validateDate(String fieldName, Date _date) {
		// If date is null; return false
		if (_date == null) {
			throw new IllegalArgumentException("Error: " + fieldName + " cannot be null.");
		}
		// If date is before the current date; return false
		// Use java.util.Date for the appointmentDate field and use before(new Date()) to check if the date is in the past.
		if (_date.before(new Date())) {
			throw new IllegalArgumentException("Error: " + fieldName + " is before the current date.");			
		}
		return true;
	}
	
	/*
	 *  Constructor
	 */
	public Appointment(String appointmentID, Date appointmentDate, String appointmentDescription) {
		super();
		
		// The appointment object shall have a required unique appointment ID string that cannot be longer than 10 characters.
		validateFieldLength("Appointment ID", appointmentID, 10);
		
		// The appointment object shall have a required appointment Date field. The appointment Date field cannot be in the past.
		validateDate("Date", appointmentDate);
		
		// The appointment object shall have a required description String field that cannot be longer than 50 characters.
		validateFieldLength("Appointment Description", appointmentDescription, 50);
		
		// If no exceptions are thrown; create appointment object
		this.appointmentID = appointmentID;
		this.appointmentDate = appointmentDate;
		this.appointmentDescription = appointmentDescription;
	}
	
	/*
	 *  Get Functions
	 */
	
	// Get appointment ID 
	public String getID() {
		return appointmentID;
	}
	
	// Get appointment date
	public Date getDate() {
		return appointmentDate;
	}
	
	// Get task description
	public String getDescription() {
		return appointmentDescription;
	}
	
	/*
	 *  Set Functions
	 */
	
	// Update appointment date
	public void setDate(Date newAppointmentDate) {
		// If new appointment date is valid; set as new appointmentDate
		validateDate("Date", newAppointmentDate);
		this.appointmentDate = newAppointmentDate;
	}
	
	// Update appointment description
	public void setDescription(String newAppointmentDescription) {
		// If new appointment description is valid; set as new appointmentDescription
		validateFieldLength("Appointment Description", newAppointmentDescription, 50);
		this.appointmentDescription = newAppointmentDescription;
	}
}

