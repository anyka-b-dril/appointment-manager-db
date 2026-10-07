//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/10/2026
//===============================================================================
package test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import main.Contact;


class ContactTest {
	
	/*
	 *  Object Tests
	 */
	
	// Successful object creation (all valid fields) and object state matches constructor inputs
	@Test
	void testSuccessfulCreation() {
		Contact contact = new Contact("1", "First", "Last", "5553334444", "1234 Lobloly Lane");
		
		assertAll("Contact fields",
	            () -> assertEquals("1", contact.getContactID(), "1"),
	            () -> assertEquals("First", contact.getFirstName(), "First"),
	            () -> assertEquals("Last", contact.getLastName(), "Last"),
	            () -> assertEquals("5553334444", contact.getPhone(), "5553334444"),
	            () -> assertEquals("1234 Lobloly Lane", contact.getAddress(), "1234 Lobloly Lane")
	    );
	}
	
	/*
	 *  Contact ID Tests
	 */
	
	// Success: Exactly 10 characters
	@Test
	void testSuccessTaskIDExactlyTenCharacters() {
		Contact contact = assertDoesNotThrow(() -> new Contact("1234567890", "StevieRay", "Vaughan", "5553334444", "1234 Lobloly Lane"), "Constructor should not throw exception for 10 character ID.");
	    
	    // Verify the state was correctly assigned
	    assertEquals("1234567890", contact.getContactID(), "Task ID should match the 10-character input.");
	}
	
	// Fail: 11 characters
	@Test
	void testFailCreationIdTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("11256984512", "StevieRay", "Vaughan", "5553334444", "1234 Lobloly Lane");
		}); 
	}
	
	// Fail: NULL
	@Test
	void testFailCreationIdNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact(null, "StevieRay", "Vaughan", "5553334444", "1234 Lobloly Lane");
		}); 
	}
	
	// Success: No Set ID Method Exists
	@Test
	void testSuccessNoSetIdMethodExists() {
	    try {
	        Contact.class.getMethod("setId", String.class);
	        fail("An 'setId' method was found, but the ID should not be updatable.");
	    } catch (NoSuchMethodException e) {
	        // Success: The method does not exist
	    }
	}
	
	/*
	 *  First Name Tests
	 */
	
	// Success: Exactly 10 characters
	@Test
	void testSuccessValidFirstName() {
		Contact testContact = new Contact("1234567890", "Christoper", "Fitzgerald", "1234445678", "12345 Why Worry Ln, City, ST");
		assertAll("Contact fields",
	            () -> assertEquals("1234567890", testContact.getContactID(), "ID should be 1234567890"),
	            () -> assertEquals("Christoper", testContact.getFirstName(), "FirstName should be to Christoper"),
	            () -> assertEquals("Fitzgerald", testContact.getLastName(), "LastName should be Fitzgerald"),
	            () -> assertEquals("1234445678", testContact.getPhone(), "Phone number should be 1234445678"),
	            () -> assertEquals("12345 Why Worry Ln, City, ST", testContact.getAddress(), "Address should be: 12345 Why Worry Ln, City, ST")
	    );
	}
	
	// Fail: 11 characters 
	@Test
	void testFailCreationFirstNameTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "StevieRayyy", "Vaughan", "5553334444", "1234 Lobloly Lane");
		}); 
	}
	
	// Fail: NULL 
	@Test
	void testFailCreationFirstNameNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", null, "Vaughan", "5553334444", "1234 Lobloly Lane");
		}); 
	}
	
	// Success: firstName can be set with valid string
		@Test
		void testValidFirstNameSet() {
			Contact testContact = new Contact("333", "Anyka", "Drilling", "5553334444", "1234 Florida Rodeo");
			// Set firstName to new value
			testContact.setFirstName("Ramsey");
			assertAll("Contact fields",
		            () -> assertEquals("333", testContact.getContactID(), "ID should be 333"),
		            () -> assertEquals("Ramsey", testContact.getFirstName(), "FirstName should be updated to Ramsey"),
		            () -> assertEquals("Drilling", testContact.getLastName(), "LastName should be updated to Drilling"),
		            () -> assertEquals("5553334444", testContact.getPhone(), "Phone number should be 5553334444"),
		            () -> assertEquals("1234 Florida Rodeo", testContact.getAddress(), "Address should be 1234 Florida Rodeo")
		    );
		}
	
	// Fail: setFirstName with invalid string (null or > 10 chars)
	@Test
	void testFailFirstNameSetTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Anyka", "Drilling", "5553334444", "1234 Florida Rodeo");
			// Set firstName to new value
			testContact.setFirstName("Bartholomew");
		});
	}	
		
	@Test
	void testFailFirstNameSetNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Anyka", "Drilling", "5553334444", "1234 Florida Rodeo");
			// Set firstName to new null
			testContact.setFirstName(null);
		});
	}
	
	/*
	 *  Last Name Tests
	 */
	// Success: Exactly 10 characters
	@Test
	void testSuccessValidLastName() {
		Contact testContact = new Contact("1234567890", "Chris", "Fitzgerald", "1234445678", "12345 Why Worry Ln, City, ST");
		assertAll("Contact fields",
	            () -> assertEquals("1234567890", testContact.getContactID(), "ID should be 333"),
	            () -> assertEquals("Chris", testContact.getFirstName(), "FirstName should be to Chris"),
	            () -> assertEquals("Fitzgerald", testContact.getLastName(), "LastName should be Fitzgerald"),
	            () -> assertEquals("1234445678", testContact.getPhone(), "Phone number should be 1234445678"),
	            () -> assertEquals("12345 Why Worry Ln, City, ST", testContact.getAddress(), "Address should be: 12345 Why Worry Ln, City, ST")
	    );
	}
	
	// Fail: 11 characters
	@Test
	void testFailLastNameTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "Anyka", "Perzynski-Drilling", "5553334444", "1234 Lobloly Lane");
		}); 
	}
	
	// Fail: NULL
	@Test
	void testFailCreationLastNameNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "Anyka", null, "5553334444", "1234 Lobloly Lane");
		}); 
	}
	
	// Success: lastName can be updated with valid string
	@Test
	void testSuccessValidLastNameSet() {
		Contact testContact = new Contact("333", "Anyka", "Drilling", "5553334444", "1234 Florida Rodeo");
		// Set lastName to new value
		testContact.setLastName("Kraya");
		assertAll("Contact fields",
	            () -> assertEquals("333", testContact.getContactID(), "ID should be 333"),
	            () -> assertEquals("Anyka", testContact.getFirstName(), "FirstName should be updated to Anyka"),
	            () -> assertEquals("Kraya", testContact.getLastName(), "LastName should be updated to Kraya"),
	            () -> assertEquals("5553334444", testContact.getPhone(), "Phone number should be 5553334444"),
	            () -> assertEquals("1234 Florida Rodeo", testContact.getAddress(), "Address should be 1234 Florida Rodeo")
	    );
	}
	
	// Fail: setlastName with invalid string (null or > 10 chars)
	@Test
	void testFailLastNameSetTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Anyka", "Drilling", "5553334444", "1234 Florida Rodeo");
			// Set lastName to new value
			testContact.setLastName("Perzynski-Drilling");
		});
	}
	
	@Test
	void testFailLastNameSet() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Anyka", "Drilling", "5553334444", "1234 Florida Rodeo");
			// Set lastName to null
			testContact.setLastName(null);
		});
	}
	
	/*
	 *  Phone Number Tests
	 */
	// Success: Exactly 10 characters
	@Test
	void testSuccessValidPhoneNumber() {
		Contact testContact = new Contact("1234567890", "Chris", "Fitzgerald", "1234445678", "12345 Why Worry Ln, City, ST");
		assertAll("Contact fields",
	            () -> assertEquals("1234567890", testContact.getContactID(), "ID should be 333"),
	            () -> assertEquals("Chris", testContact.getFirstName(), "FirstName should be to Chris"),
	            () -> assertEquals("Fitzgerald", testContact.getLastName(), "LastName should be Fitzgerald"),
	            () -> assertEquals("1234445678", testContact.getPhone(), "Phone number should be 1234445678"),
	            () -> assertEquals("12345 Why Worry Ln, City, ST", testContact.getAddress(), "Address should be: 12345 Why Worry Ln, City, ST")
	    );
	}
	
	// Fail: 11 characters
	@Test
	void testFailCreationPhoneTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "Carlos", "Santana", "18006666666", "1234 Lobloly Lane");
		}); 
	}
	
	// Fail: 9 characters
	@Test
	void testFailCreationPhoneTooshort() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "Carlos", "Santana", "850333333", "1234 Lobloly Lane");
		}); 
	}
	
	// Fail: NULL
	@Test
	void testFailCreationPhoneNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "Carlos", "Santana", null, "1234 Lobloly Lane");
		}); 
	}
	
	// Phone can be updated with valid string
	@Test
	void testSuccessValidPhoneSet() {
		Contact testContact = new Contact("333", "Jimi", "Hendrix", "5553334444", "1234 Lobloly Lane");
		// Set phone to new value
		testContact.setPhone("8503333636");
		assertAll("Contact fields",
	            () -> assertEquals("333", testContact.getContactID(), "ID should be 333"),
	            () -> assertEquals("Jimi", testContact.getFirstName(), "FirstName should be Jimi"),
	            () -> assertEquals("Hendrix", testContact.getLastName(), "LastName should be Hendrix"),
	            () -> assertEquals("8503333636", testContact.getPhone(), "Phone number should be 8503333636"),
	            () -> assertEquals("1234 Lobloly Lane", testContact.getAddress(), "Address should be 1234 Lobloly Lane")
	    );
	}
	
	// Fail: setPhone with invalid string (null or != 10 chars)
	@Test
	void testFailPhoneSetTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Jimi", "Hendrix", "5553334444", "1234 Lobloly Lane");
			// Set phone to new value
			testContact.setPhone("555333444410");
			
		});
	}
	
	@Test
	void testFailPhoneSetTooShort() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Jimi", "Hendrix", "5553334444", "1234 Lobloly Lane");
			// Set phone to new value
			testContact.setPhone("123456789");
			
		});
	}
	
	@Test
	void testFailPhoneSetNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Jimi", "Hendrix", "5553334444", "1234 Lobloly Lane");
			// Set phone to null
			testContact.setPhone(null);
			
		});
	}
			
	/*
	 *  Address Tests
	 */
	// Success: Exactly 10 characters
	@Test
	void testSuccessValidAddress() {
		Contact testContact = new Contact("1234567890", "Chris", "Rich", "1234445678", "12344 Nowhere Ln, Fiction, CA");
		assertAll("Contact fields",
	            () -> assertEquals("1234567890", testContact.getContactID(), "ID should be 333"),
	            () -> assertEquals("Chris", testContact.getFirstName(), "FirstName should be to Chris"),
	            () -> assertEquals("Rich", testContact.getLastName(), "LastName should be Rich"),
	            () -> assertEquals("1234445678", testContact.getPhone(), "Phone number should be 1234445678"),
	            () -> assertEquals("12344 Nowhere Ln, Fiction, CA", testContact.getAddress(), "Address should be: 12344 Nowhere Ln, Fiction, CA")
	    );
	}
	
	// Fail: 31 characters
	@Test
	void testFailCreationAddressTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "Carlos", "Santana", "18006666666", "1234 RollingStones Magazine Headquaters");
		}); 
	}
	
	// Fail: NULL
	@Test
	void testFailCreationAddressNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("1", "Carlos", "Santana", "5553334444", null);
		}); 
	}
	
	// Success: address can be updated with valid string
	@Test
	void testSuccessValidAddressSet() {
		Contact testContact = new Contact("333", "Jimi", "Hendrix", "5553334444", "1234 Lobloly Lane");
		// Set address to new value
		testContact.setAddress("123444 Nowhere Ln, Fiction, CA");
		assertAll("Contact fields",
	            () -> assertEquals("333", testContact.getContactID(), "ID should be 333"),
	            () -> assertEquals("Jimi", testContact.getFirstName(), "FirstName should be Jimi"),
	            () -> assertEquals("Hendrix", testContact.getLastName(), "LastName should be Hendrix"),
	            () -> assertEquals("5553334444", testContact.getPhone(), "Phone number should be 5553334444"),
	            () -> assertEquals("123444 Nowhere Ln, Fiction, CA", testContact.getAddress(), "Address should be 12344 Nowhere Ln, Fiction, CA")
	    );
	}
	
	// Fail: address cannot be updated with invalid string (null or < 30 chars)
	@Test
	void testFailAddressSetTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Jimi", "Hendrix", "5553334444", "1234 Lobloly Lane");
			// Set address to new value
			testContact.setAddress("40445 NW That Ain't It Road, FL");
			
		});
	}
	
	// Fail: address cannot be updated with null value
	@Test
	void testFailAddressSet() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Contact testContact = new Contact("333", "Jimi", "Hendrix", "5553334444", "1234 Lobloly Lane");
			// Set address to null
			testContact.setAddress(null);
			
		});
	}
}
