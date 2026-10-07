//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/10/2026
//===============================================================================
package main;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class ContactService {
	
	// Contact "Database" with ID as the key
	private Map<String, Contact> contactBook = new HashMap<>();
	// Initialize random integer for unique contact ID
	private final Random random = new Random();
	
	
	/*
	 *  Add contact with a unique ID
	 */
	
	public String addContact(String firstName, String lastName, String phone, String address) {
		int rawContactID;
		String contactID;
		
		// Validate current random generated id is not in use
		// Otherwise, we risk overwriting data
		do {
			rawContactID = random.nextInt(Integer.MAX_VALUE); // Generate a number between 0-2.1 billion (all are 10 digits or fewer)
			// Format it to be exactly 10 digits with leading zeros
			contactID = String.format("%010d", rawContactID);
		} while (contactBook.containsKey(contactID));

		// Create new contact object
		Contact newContact = new Contact(String.valueOf(contactID), firstName, lastName, phone, address);
		// Add contact to contact book
		contactBook.put(String.valueOf(contactID), newContact);
		
		// Celebrate success
		System.out.println("Created Contact: " + firstName + " " + lastName + " with ID: " + contactID);
		
		// Return the task ID so the application knows what was created
		return contactID;
	}
	
	/*
	 *  Delete contact by unique ID
	 */
	
	public void deleteContact(String contactID) {
		if (contactBook.containsKey(contactID)) {
			// Record deleted items
			Contact removed = contactBook.remove(contactID);
			// Report deletion
			System.out.println("Deleted Contact: " + removed.getFirstName() + " " + removed.getLastName() + " with ID: " + removed.getContactID());
		}
		else {
			// Report error and continue
			throw new IllegalArgumentException("Error: ID " + contactID + " not found.");
		}
		
	}
	
	/*
	 *  Update contact by unique ID
	 */
	
	// Update First Name
	public void updateFirstName(String contactID, String newName) {
		// Create temporary reference
		Contact temp = contactBook.get(contactID);
		
		// If an id match was found, update firstName with newName
		if (temp != null) {
			temp.setFirstName(newName);
			// Report success
			System.out.println("First name updated for ID: " + contactID);
		}
		else {
			throw new IllegalArgumentException("Error: ID " + contactID + "not found.");
		}
	}
	
	// Update Last Name
	public void updateLastName(String contactID, String newName) {
		// Create temporary reference
		Contact temp = contactBook.get(contactID);
		
		// If an id match was found, update LastName with newName
		if (temp != null) {
			temp.setLastName(newName);
			// Report success
			System.out.println("Last name updated for ID: " + contactID);
		}
		else {
			throw new IllegalArgumentException("Error: ID " + contactID + "not found.");
		}
	}
	
	// Update Phone
	public void updatePhone(String contactID, String newPhone) {
		// Create temporary reference
		Contact temp = contactBook.get(contactID);
		
		// If an id match was found, update phone with newPhone
		if (temp != null) {
			temp.setPhone(newPhone);
			// Report success
			System.out.println("Phone number updated for ID: " + contactID);
		}
		else {
			throw new IllegalArgumentException("Error: ID " + contactID + "not found.");
		}
	}
	
	// Update Address
	public void updateAddress(String contactID, String newAddress) {
		// Create temporary reference
		Contact temp = contactBook.get(contactID);
		
		// If an id match was found, update address with newAddress
		if (temp != null) {
			temp.setAddress(newAddress);
			// Report success
			System.out.println("Address updated for ID: " + contactID);
		}
		else {
			throw new IllegalArgumentException("Error: ID " + contactID + "not found.");
		}
	}
	
	/*
	 *  Get ContactBook contents
	 */
	public Map<String, Contact> getContactBook() {
		return contactBook;
	}
}
