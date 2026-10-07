//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================
package com.sparks.sparklink.mapper;

import com.sparks.sparklink.model.Appointment;
import org.bson.Document;

// Ref: https://medium.com/@anandjeyaseelan10/spring-boot-project-structure-explained-best-practices-c2ba46ea57eb

public class AppointmentMapper {
	// Convert Java to BSON
	public static Document toDocument(Appointment appointment) {
		// If object is null, return null; else map
		if (appointment.equals(null)) return null;
		return new Document("_id", appointment.getID())
				.append("appointmentDate", appointment.getDate())
				.append("description", appointment.getDescription());
	}
	
	// Convert BSON to Java
	public static Appointment toEntity(Document doc) {
		// If object is null, return null; else map
		if (doc.equals(null)) return null;
		return new Appointment(
				doc.getString("_id"), 
				doc.getDate("appointmentDate"), 
				doc.getString("description"));
	}
}
