//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
// Ref: https://www.w3schools.com/java/java_interface.asp
// Ref: https://www.reddit.com/r/golang/comments/182rdrb/idiomatic_way_of_creating_an_repositorymanager/

package com.sparks.sparklink.repository;

import java.util.List;
import java.util.Optional;

import com.sparks.sparklink.model.Appointment;

// Establish CRUD connections: 
public interface AppointmentRepository extends AutoCloseable {
	// Create
	Appointment save(Appointment appointment);
	// Read
	Optional<Appointment> findById(String id);
	List<Appointment> findAll();
	// Update
	boolean updateById(Appointment appointment);
	// Delete
	void deleteById(String id);
	void deleteAll();
	// Closeable
	@Override
	void close();
}
