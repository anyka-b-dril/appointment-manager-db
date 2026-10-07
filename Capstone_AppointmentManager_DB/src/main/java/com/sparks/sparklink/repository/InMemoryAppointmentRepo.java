//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
// Ref: https://www.w3schools.com/java/java_interface.asp
// Ref: https://www.reddit.com/r/golang/comments/182rdrb/idiomatic_way_of_creating_an_repositorymanager/

package com.sparks.sparklink.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sparks.sparklink.model.Appointment;

public class InMemoryAppointmentRepo implements AppointmentRepository, AutoCloseable {
	// Initialize memory structure
	private Map<String, Appointment> appointmentList = new HashMap<>();

	// Add appointment to list
	@Override
	public Appointment save(Appointment appointment) {
		appointmentList.put(appointment.getID(), appointment);
		return appointment;
	}

	// Get appointment in list
	@Override
	public Optional<Appointment> findById(String id) {
		return Optional.ofNullable(appointmentList.get(id));
	}

	// Get ALL appointments in list
	@Override
	public List<Appointment> findAll() {
		 return new ArrayList<>(appointmentList.values());
	}
	
	// Update appointment in list
	@Override
	public boolean updateById(Appointment appointment) {
		// If object is null, return false
		if (appointment == null || appointment.getID() == null) {
			return false;
		}
		// If appointment ID does not exist, return false
		if (!appointmentList.containsKey(appointment.getID())) {
			return false;
		}
		// Else add appointment
		appointmentList.put(appointment.getID(), appointment);
		return true;
	}

	// Delete appointment in list
	@Override
	public void deleteById(String id) {
		appointmentList.remove(id);
		
	}
	
	// Delete appointment ALL appointments in list
	@Override
	public void deleteAll() {
		this.appointmentList.clear();
	}

	@Override
	public void close() {}
}
