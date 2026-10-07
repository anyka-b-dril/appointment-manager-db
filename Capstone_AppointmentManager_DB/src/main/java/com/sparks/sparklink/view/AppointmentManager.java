//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: Capstone
// Date:  9/2026
//===============================================================================

package com.sparks.sparklink.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.sparks.sparklink.model.Appointment;
import com.sparks.sparklink.service.AppointmentService;

public class AppointmentManager {
	// Initialize services
	private final AppointmentService service;
	private final Scanner input;
	private final SimpleDateFormat dateFormat;
	
	
	public AppointmentManager(AppointmentService service) {
		this.service = service;
		this.input = new Scanner(System.in);
		this.dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
	}
	
	/*
	 *  Create an appointment
	 */
	private void createAppointment() {
		try {
			// Get date
			System.out.println("Enter date (MM-DD-YYYY): ");
			String date = input.nextLine().trim();
			// Get time
			System.out.println("Enter time (hh:mm AM/PM): ");
			String time = input.nextLine().trim();
			
			// Set appointment date/time
			Date datetime = dateFormat.parse(date + " " + time);
			
			// Get description
			System.out.println("Enter description (max 50 chars): ");
			String description = input.nextLine().trim();
			
			// Create appointment
			Appointment newAppointment = service.addAppointment(datetime, description);
			System.out.println("Appointment created successfully! ID: " + newAppointment.getID() + "\n");
			
		}
		// Error for invalid date format and other exceptions
		catch (ParseException e){
			System.out.println("Invalid date format. Please use MM-DD-YYYY hh:mm AM/PM." + "\n");
		}
		catch (IllegalArgumentException e) {
			System.out.println("Error creating appointment: " + e.getMessage() + "\n");
		}
	}
	
	/*
	 *  Print all Appointments
	 */
	private void listAllAppointments() {
		// Get all appointments
		List<Appointment> list = service.getAllAppointments();
		
		// If list is empty, notify user; else print list
		if(list.isEmpty()) {
			System.out.println("There are no appointments on file." + "\n");
		}
		else {
			// Table header
			System.out.println(" ".repeat(33) + "Current Appointments");
			System.out.println("=".repeat(86));
			System.out.println("   ID       | Date                | Description");

			// for each appointment in the list, print appointment details
			for (Appointment app : list) {
				System.out.println("-".repeat(86));
				System.out.print(" " + app.getID());
				System.out.print(" | " + dateFormat.format(app.getDate()));
				System.out.println(" | " + app.getDescription());
			}
			// Table footer
			System.out.println("=".repeat(86) + "\n");
		}
	}
	
	/*
	 *  Print appointment
	 */
	private void findAppointment() {
		// Prompt user
		System.out.println("Enter the appointment ID: ");
		String id = input.nextLine().trim();
		
		Optional<Appointment> appointment = service.findAppointment(id);
		// If appointment is found, display to user
		if(appointment.isPresent()) {
			Appointment app = appointment.get();
			
			System.out.println(" ".repeat(40) + "Found");
			System.out.println("=".repeat(86));
			System.out.print("ID: " + app.getID());
			System.out.print(" | Date: " + dateFormat.format(app.getDate()));
			System.out.println(" | Description: " + app.getDescription());
			System.out.println("=".repeat(86) + "\n");
		}
		else {
			System.out.println("No appointment found with ID: " + id + "\n");
		}
		
	}
	
	/*
	 *  Update appointment
	 */
	private void updateAppointment() {
		// Prompt user
		System.out.println("Enter the appointment ID: ");
		String id = input.nextLine().trim();
		
		Optional<Appointment> appointment = service.findAppointment(id);
		// If appointment is found, prompt for new appointment details
		if(appointment.isPresent()) {
			Date newDateTime;
			String newDesc;
			
			// Get existing appointment details
			SimpleDateFormat currDate = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat currTime = new SimpleDateFormat("hh:mm a");
			
			Appointment app = appointment.get();
			
			// Display queried appointment details
			System.out.println(" ".repeat(40) + "Found");
			System.out.println("=".repeat(86));
			System.out.print("ID: " + app.getID());
			System.out.print(" | Date: " + dateFormat.format(app.getDate()));
			System.out.println(" | Description: " + app.getDescription());
			System.out.println("=".repeat(86) + "\n");
			
			// Get date
			System.out.println("Enter date (MM-DD-YYYY hh:mm AM/PM) [Press ENTER to keep current]: ");
			String date = input.nextLine().trim();
			// If ENTER, use existing date
			if (date.isEmpty()) {
				date = currDate.format(app.getDate());
			}
			
			// Get time
			System.out.println("Enter time (hh:mm AM/PM) [Press ENTER to keep current]: ");
			String time = input.nextLine().trim();
			// If ENTER, use existing time
			if (time.isEmpty()) {
				time = currTime.format(app.getDate());
			}
			
			// Format date
			try {
				newDateTime = dateFormat.parse(date + " " + time);
			} catch (ParseException e) {
				System.out.println("Invalid date format. Please use MM-DD-YYYY hh:mm AM/PM." + "\n");
				return;
			}
			
			// Get description
			System.out.println("Enter description (max 50 chars) [Press ENTER to keep current]: ");
			// Set appointment description
			newDesc = input.nextLine().trim();
			
			// If desc is empty, use existing description
			if (newDesc.isEmpty()) {
				newDesc = app.getDescription();
			}
			
			// Update
			boolean success = service.updateAppointment(id, newDateTime, newDesc);
			// Display success or failure
			if(success) {
				System.out.println("Appointment with ID: " + id + " was updated successfully! \n");
			}
			else {
				System.out.println("Error: Failed to update appointment. Please try again. \n");
			}
			// END loop
			return;
		}
		// else no id found, print error
		else {
			System.out.println("Error: No appointment found with ID: " + id + "\n");
		}
	}
	
	/*
	 *  Delete appointment
	 */
	private void deleteAppointment() {
		// Prompt user
		System.out.println("Enter the appointment ID: ");
		String id = input.nextLine().trim();
		
		try {
			service.deleteAppointment(id);
			System.out.println("\n");
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.out.println("\n");
		}		
	}
	
	/*
	 *  Print user menu
	 */
	public void printMenuOptions () {
		System.out.println("           MENU            ");
		System.out.println("0~~~~~~~~~~~~~~~~~~~~~~~~~~0");
		System.out.println(" 1. Create Appointment");
		System.out.println(" 2. List all Appointments");
		System.out.println(" 3. Find an Appointment");
		System.out.println(" 4. Update an Appointment");
		System.out.println(" 5. Delete an Appointment");
		System.out.println(" 9. Exit");
		System.out.println("0~~~~~~~~~~~~~~~~~~~~~~~~~~0");
	}
	
	/*
	 *  Welcome banner
	 */
	// Credit: Untitled ASCII Art (2014) by Joan G. Stark (Spunk) 
	// asciiart.eu/art/fa4826277d48ef8c 
	public void welcomeBanner() {
		String banner = """
	              .
	              .
	              |
	        '.  _..._  .'
	          .'     '.
	     '-. /         \\ .-'
	    _ _ ;           ;  _ _
    ===========================
       S P A R K   L I N K
    ===========================
	            """;
				
	System.out.println(banner);		
	}	
	
	/*
	 *  Action menu
	 */
	public void menu () {
		int option;
		
		welcomeBanner();
		
		// while user input is not nine, display manager menu
		do {
			printMenuOptions();
			
			// If input is not an number, display error
			while (!input.hasNextInt()) {
				System.out.println("Error: Please type a number.");
				System.out.println(" ");
				input.nextLine();
			}
		
			option = input.nextInt();
			input.nextLine();
		
			switch (option) {
				case 1: 
					// Create Appointment
					createAppointment();
					break;
				case 2: 
					// Print all Appointments
					listAllAppointments();
					break;
				case 3: 
					// Find an Appointment
					findAppointment();
					break;
				case 4:
					// Update an Appointment
					updateAppointment();
					break;
				case 5: 
					// Delete an Appointment
					deleteAppointment();
					break;
				case 9:
					// Exit
					System.out.println("Exititing the application. Goodbye!");
					break;
				default:
					// Default option
					System.out.println("Please input a number between 1-4 or 9.");
					break;
				}
		} while (option != 9);
	}
}
