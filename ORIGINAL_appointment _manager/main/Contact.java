//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/10/2026
//===============================================================================
package main;

public class Contact {
	
	private final String contactID; //strictly enforce "not updatable" properties
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	
	
	/*
	 *  Validation Functions 
	 */
	
	private boolean validateFieldLength(String fieldName, String field, int limit) {
		// Explicit error handling for null and above limit values
		// If the target field is null; return false
		if (field == null) {
			throw new IllegalArgumentException("Error: " + fieldName + " cannot be null.");
		}
		// If the target field is greater than its set character limit; return false
		if (field.length() > limit) {
			throw new IllegalArgumentException("Error: " + fieldName + " is too long.");
		}
		return true;
	}
	
	private boolean validatePhoneNumber(String phone, int limit) {
		// If phone is null; return false
		if(phone == null) {
			throw new IllegalArgumentException("Error: phone number cannot be null.");
		}
		// If phone is not 10 digits; return false
		if(phone.length() != limit) {
			throw new IllegalArgumentException("Error: given phone number not ten digits long.");
		}
		return true;
	}
	
	/*
	 *  Constructor
	 */
	
	public Contact(String contactID, String firstName, String lastName, String phone, String address) {
		super();
		// Validate from left to right
		// If even one field is invalid, throw exception
		// Do not accept less than 5 fields
		
		// The contact object shall have a required unique contact ID string that cannot be longer than 10 characters. 
		// The contact ID shall not be null and shall not be updatable.
		validateFieldLength("Contact ID", contactID, 10);
		
		// The contact object shall have a required firstName String field that cannot be longer than 10 characters. The firstName field shall not be null.
		validateFieldLength("First name", firstName, 10);
		
		// The contact object shall have a required lastName String field that cannot be longer than 10 characters. The lastName field shall not be null
		validateFieldLength("Last name", lastName, 10);
		
		// The contact object shall have a required phone String field that must be exactly 10 digits. The phone field shall not be null.
		validatePhoneNumber(phone, 10);
		
		// The contact object shall have a required address field that must be no longer than 30 characters. The address field shall not be null.
		validateFieldLength("Address", address, 30);
		
		// If no exceptions are thrown; create contact object
		this.contactID = contactID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
	}
	
	
	/*
	 *  Get Functions
	 */
	
	// Get contact ID
	public String getContactID() {
		return contactID;
	}
	
	// Get Contact first name
	public String getFirstName() {
		return firstName;
	}
	
	// Get Contact last name
	public String getLastName() {
		return lastName;
	}
	
	// Get Contact phone
	public String getPhone() {
		return phone;
	}
	
	// Get Contact address
	public String getAddress() {
		return address;
	}
	
	
	/*
	 *  Set Functions
	 */
	
	// Set Contact first name
	public void setFirstName(String firstName) {
		// The contact object shall have a required firstName String field that cannot be longer than 10 characters. The firstName field shall not be null.
		validateFieldLength("First name", firstName, 10);
		this.firstName = firstName;
	}
	
	// Set Contact last name
	public void setLastName(String lastName) {
		// The contact object shall have a required firstName String field that cannot be longer than 10 characters. The firstName field shall not be null.
		validateFieldLength("Last name", lastName, 10);
		this.lastName = lastName;
	}
	
	// Set Contact phone
	public void setPhone(String phone) {
		// The contact object shall have a required phone String field that must be exactly 10 digits. The phone field shall not be null.
		validatePhoneNumber(phone, 10);
		this.phone = phone;
	}
	
	// Set Contact address
	public void setAddress(String address) {
		// The contact object shall have a required address field that must be no longer than 30 characters. The address field shall not be null.
		validateFieldLength("Address", address, 30);
		this.address = address;
	}

}
