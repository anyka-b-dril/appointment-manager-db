//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
package com.sparks.sparklink.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.sparks.sparklink.model.Appointment;
import com.sparks.sparklink.repository.AppointmentRepository;


public class AppointmentService {
	// Initialize random integer for unique Appointment ID
	private final Random randomInt = new Random();
	
	// Initialize Interface
	private final AppointmentRepository appointmentRepo;
	
	// Construct memory
	public AppointmentService(AppointmentRepository appointmentRepo) {
		this.appointmentRepo = appointmentRepo;
	}
	
	//=======================================================
	//  METHODS
	//=======================================================
	
	/*
	 *  Generate Random ID
	 */
	private String generateAppointmentId() {
		int rawAppointmentID;
		String appointmentID;
		
		// Validate current random generated id is not in use
		// Otherwise, we risk overwriting data
		do {
			rawAppointmentID = randomInt.nextInt(Integer.MAX_VALUE); // Generate a number between 0-2.1 billion (all are 10 digits or fewer)
			// Format it to be exactly 10 digits with leading zeros
			appointmentID = String.format("%010d", rawAppointmentID);
		} while (appointmentRepo.findById(appointmentID).isPresent());
		
		return appointmentID;
	}
	
	/*
	 *  Add Appointment with a unique ID
	 */
	public Appointment addAppointment(Date appointmentDate, String appointmentDescription) {
		// Create ID
		String appointmentID = generateAppointmentId();
		// Create appointment
		if (appointmentDate != null && appointmentDescription != null) {
			Appointment appointment = new Appointment(appointmentID, appointmentDate, appointmentDescription);
			// Save appointment
			return appointmentRepo.save(appointment);
		}
		// handle null pointers
		else {
			throw new IllegalArgumentException("Appointment date and description cannot be null.");
		}
	}
	
	/*
	 *  Delete Appointment by unique ID
	 */
	public void deleteAppointment(String appointmentID) {
		// Fetch queried appointment
		Optional<Appointment> remove = appointmentRepo.findById(appointmentID);
		
		// If the appointmentID exists, delete the appointment and report success
		if (remove.isPresent()) {
			// Delete
			appointmentRepo.deleteById(appointmentID);
			
			//System.out.println(appointmentRepo.findAll());
			
			// If the appointment was successfully removed, print success
			if(appointmentRepo.findById(appointmentID).isEmpty()) {
				System.out.println("Deleted Appointment: ID: " + (remove.get().getID()) + ", Date: " + (remove.get().getDate()));
			}
			else {
				throw new IllegalArgumentException("Error: ID " + appointmentID + " was not able to be deleted.");
			}
		}
		else if (appointmentID == null) {
			throw new IllegalArgumentException("Error: ID cannot be null." );
		}
		else {
			throw new IllegalArgumentException("Error: ID " + appointmentID + " not found.");
		}
	}
	
	/*
	 *  Get Appointment by unique ID
	 */
	public Optional<Appointment> findAppointment(String appointmentID) {
		// If the appointmentID matched and existing appointment Id, return appointment
		if(appointmentRepo.findById(appointmentID).isPresent()) {
			return appointmentRepo.findById(appointmentID);
		}
		else {
			System.out.println("Error: ID " + appointmentID + " was not found.");
			return Optional.empty();
		}
	}
	
	/*
	 *  Get all Appointments
	 */
	public List<Appointment> getAllAppointments() {
		return appointmentRepo.findAll();
	}
	
	/*
	 *   Update Appointment by unique ID
	 */
	public boolean updateAppointment(String id, Date newDate, String newDescription) {
		// Get appointment 
		Optional<Appointment> app = findAppointment(id);
		// If appointment was not found, return false
		if (app.isEmpty()) {
			return false;
		}
		// Else
		// Cast to appointment object
		Appointment appointment = app.get();
		
		// If updating date, validate and update date field
		// If date is before the current date; return false
		if (newDate != null) {
			if (newDate.before(new Date())) {
				throw new IllegalArgumentException("Error: new date is before the current date.");			
			}
			appointment.setDate(newDate);
		}
		
		// If updating the description, validate and update the desc field
		if (newDescription != null && !newDescription.isEmpty()) {
			if (newDescription.length() > 50) {
				throw new IllegalArgumentException("Error: Description is too long.");
			}
			appointment.setDescription(newDescription);
		}
		
		// Update the appointment in list
		// Returns object 
		return appointmentRepo.updateById(appointment);
	}
}
