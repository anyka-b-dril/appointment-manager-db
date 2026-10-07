//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/10/2026
//===============================================================================
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Added MethodOrderer as an easy way to organize the console output
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import main.Contact;
import main.ContactService;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactServiceTest {
    private ContactService service;

    @BeforeEach
    void setUp() {
        service = new ContactService();
    }

	/*
	 *  Contact Add Tests
	 */
    
    // Success: Add contact with valid values
    @Test
    @Order(1)
    void testSuccessAddContact() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Since IDs are random, verify it was added to the book
        assertFalse(service.getContactBook().isEmpty());
        assertEquals(1, service.getContactBook().size());
        
        // Get contact ID
        Contact contact = service.getContactBook().values().iterator().next();
        String id = contact.getContactID();
        // Validate correct data was added
        assertAll("Contact fields",
	            () -> assertTrue(service.getContactBook().containsKey(id)),
	            () -> assertEquals("John", service.getContactBook().get(id).getFirstName(), "FirstName should be to John"),
	            () -> assertEquals("Doe", service.getContactBook().get(id).getLastName(), "LastName should be Doe"),
	            () -> assertEquals("1234567890", service.getContactBook().get(id).getPhone(), "Phone number should be 1234567890"),
	            () -> assertEquals("123 Main St", service.getContactBook().get(id).getAddress(), "Address should be: 123 Main St")
	    ); 
    }
    
    // Success: Verify the generated ID is exactly 10 characters (check padding).
    @Test
    @Order(2)
    void testSuccessContactIDFormat() {
    	 service.addContact("John", "Doe", "1234567890", "123 Main St");
    	// Get contact ID
    	 Contact contact = service.getContactBook().values().iterator().next();
         String id = contact.getContactID();
         
         assertEquals(10, id.length(), "Generated ID must be exactly 10 characters long.");
         assertTrue(id.matches("\\d{10}"), "Generated ID should be a 10-digit numeric string.");
    }
    
    // Fail: Add contact with invalid values
    @Test
    @Order(3)
    void testFailAddContactFirstNameTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("Bartholomew", "Doe", "1234567890", "123 Main St");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(4)
    void testFailAddContactFirstNameNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact(null, "Doe", "1234567890", "123 Main St");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(5)
    void testFailAddContactLastNameTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("John", "Bartholomew", "1234567890", "123 Main St");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(6)
    void testFailAddContactLastNameNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("John", null, "1234567890", "123 Main St");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(7)
    void testFailAddContactPhoneTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("John", "Doe", "12345678901", "123 Main St");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(8)
    void testFailAddContactPhoneNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("John", "Doe", null, "123 Main St");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(9)
    void testFailAddContactPhoneTooShort() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("John", "Doe", "123456789", "123 Main St");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(10)
    void testFailAddContactAddressTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("John", "Doe", "1234567890", "40445 NW That Ain't It Road, FL");
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    @Test
    @Order(11)
    void testFailAddContactAddressNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addContact("John", "Doe", "1234567890", null);
		});
        
        // Since IDs are random, verify it was NOT added to the book
        assertTrue(service.getContactBook().isEmpty());
    }
    
    /*
	 *  Contact Unique ID Tests
	 */
    
    // Success: Add a high volume of tasks (e.g., 100) and verify the Map size is exactly 100.
    // 			Verify that the do-while loop prevents duplicate IDs from overwriting data.
    @Test
    @Order(12)
    void testSuccessIdUniqueness() {
    	int iteration = 100; //For 100 contacts
    	
    	// Create 100 contacts
    	for (int i = 0; i < iteration; i++) {
    		service.addContact("John", "Doe" + i, "1234567890", "123 Main St");
    	}
    	// If all IDs are unique, the size should equal the set iteration number
    	// else, some IDs are being overwritten :(((
    	assertEquals(iteration, service.getContactBook().size(), "The contact book size should equal the iteration nummber");
    }
    
	/*
	 *  Contact Update First Name Tests
	 */
    
    // Success: Update firstName with valid string
    @Test
    @Order(13)
    void testSucccessUpdateFirstName() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Get id from contactBook (First ID in this case)
        String id = service.getContactBook().keySet().iterator().next();
        service.updateFirstName(id, "Jonathan");
        
        //Verify name John was updated to Jonathan
        assertEquals("Jonathan", service.getContactBook().get(id).getFirstName());
    }
    
    // Fail: Update firstName for an ID that does not exist.
    @Test
    @Order(14)
    void testFailUpdateFirstNameNonExist() {
    	String id = service.addContact("John", "Doe", "1234567890", "123 Main St");
        // Attempt update with bad id
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updateFirstName("1234567890", "Jonas");
    	});
        
        //Verify name John was NOT updated
        assertEquals("John", service.getContactBook().get(id).getFirstName());
    }
    
    // Fail: Update firstName with invalid string
    @Test
    @Order(15)
    void testFailUpdateFirstNameTooLong() {
    	String id = service.addContact("John", "Doe", "1234567890", "123 Main St");
        // Attempt to update first name with < 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName(id, "AnykaIsWayTooLong");
        });
        
        //Verify name John was NOT updated
        assertEquals("John", service.getContactBook().get(id).getFirstName());
    }
    
    /*
	 *  Contact Update Last Name Tests
	 */
    
    // Success: Update lastName with valid string
    @Test
    @Order(16)
    void testSuccessUpdateLastName() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Get id from contactBook (First ID in this case)
        String id = service.getContactBook().keySet().iterator().next();
        service.updateLastName(id, "Dothan");
        
        //Verify name Doe was updated to Dothan
        assertEquals("Dothan", service.getContactBook().get(id).getLastName());
    }
    
    // Fail: Update lastName for an ID that does not exist.
    @Test
    @Order(17)
    void testFailUpdateLastNameNonExist() {
    	String id = service.addContact("John", "Doe", "1234567890", "123 Main St");
        // Attempt update with bad id
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updateLastName("1234567890", "Dorthy");
    	});
        
        //Verify name Doe was NOT updated
        assertEquals("Doe", service.getContactBook().get(id).getLastName());
    }
    
    // Fail: Update lastName with invalid string
    @Test
    @Order(18)
    void testFailUpdateLastNameTooLong() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Get id from contactBook (First ID in this case)
        String id = service.getContactBook().keySet().iterator().next();
        // Attempt to update last name with < 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateLastName(id, "AnykaIsWayTooLong");
        });
        
        //Verify name Doe was NOT updated
        assertEquals("Doe", service.getContactBook().get(id).getLastName());
    }
    
    /*
	 *  Contact Update Phone Tests
	 */
    
    // Success: Update phone with valid string
    @Test
    @Order(19)
    void testSuccessUpdatePhone() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Get id from contactBook (First ID in this case)
        String id = service.getContactBook().keySet().iterator().next();
        service.updatePhone(id, "1234567891");
        
        //Verify phone 1234567890 was updated to 1234567891
        assertEquals("1234567891", service.getContactBook().get(id).getPhone());
    }
    
    // Fail: Update phone for an ID that does not exist.
    @Test
    @Order(20)
    void testFailUpdatePhoneNonExist() {
    	String id = service.addContact("John", "Doe", "1234567890", "123 Main St");
        // Attempt update with bad id
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updatePhone("1234567890", "9874561233");
    	});
        
        //Verify phone 1234567890 was NOT updated
        assertEquals("1234567890", service.getContactBook().get(id).getPhone());
    }
    
    // Fail: Update phone with invalid string
    @Test
    @Order(21)
    void testFailUpdatePhoneTooShort() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Get id from contactBook (First ID in this case)
        String id = service.getContactBook().keySet().iterator().next();
        // Attempt to update phone with > 10 characters
        assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhone(id, "123456789");
        });
        
        //Verify phone 1234567890 was NOT updated to 123456789
        assertEquals("1234567890", service.getContactBook().get(id).getPhone());
    }
    
    /*
	 *  Contact Update Address Tests
	 */
    
    // Success: Update address with valid string
    @Test
    @Order(22)
    void testSuccessUpdateAddress() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Get id from contactBook (First ID in this case)
        String id = service.getContactBook().keySet().iterator().next();
        service.updateAddress(id, "456 Main St Ave");
        
        //Verify address 123 Main St was updated to 456 Main St Ave
        assertEquals("456 Main St Ave", service.getContactBook().get(id).getAddress());
    }
    
    // Fail: Update address for an ID that does not exist.
    @Test
    @Order(23)
    void testFailUpdateAddressNonExist() {
    	String id = service.addContact("John", "Doe", "1234567890", "123 Main St");
        // Attempt update with bad id
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updateAddress("1234567890", "789 Main Rd");
    	});
        
        //Verify address 123 Main St was NOT updated
        assertEquals("123 Main St", service.getContactBook().get(id).getAddress());
    }
    
    // Fail: Update address with invalid string
    @Test
    @Order(24)
    void testFailUpdateAddressTooLong() {
        service.addContact("John", "Doe", "1234567890", "123 Main St");
        
        // Get id from contactBook (First ID in this case)
        String id = service.getContactBook().keySet().iterator().next();
        // Attempt to update address with < 30 characters
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateAddress(id, "1234 RollingStones Magazine Headquaters");
        });
        
        //Verify address 123 Main St was NOT updated to 1234 RollingStones Magazine Headquaters
        assertEquals("123 Main St", service.getContactBook().get(id).getAddress());
    }

	/*
	 *  Contact Delete Tests
	 */
    
    // Success: Delete contact by valid ID
    @Test
    @Order(25)
    void testSuccessDeleteContact() {
        service.addContact("Jane", "Doe", "0987654321", "456 High St");
        String id = service.getContactBook().keySet().iterator().next();
        
        service.deleteContact(id);
        
        // Assert the map is now empty
        assertTrue(service.getContactBook().isEmpty());
    }
    
    // Fail: Delete contact by non-existant ID
    @Test
    @Order(26)
    void testFailDeleteContact() {
        service.addContact("Jane", "Doe", "0987654321", "456 High St");
        assertThrows(IllegalArgumentException.class, () -> {
        	service.deleteContact("1");
        });
        
        // Assert the map is NOT empty
        assertFalse(service.getContactBook().isEmpty());
    }
    
    // Success: Delete a contact by a know ID with multiple contacts in the Book
    @Test
    @Order(27)
    void testSuccessDeleteMultipleContacts() {
    	// Create two contacts and store returned IDs
    	String idDelete = service.addContact("Jane", "Doe", "0987654321", "456 High St");
    	String idKeep = service.addContact("John", "Smith", "1234567890", "420 Logo Rd");
    	
    	// Delete first ID
    	service.deleteContact(idDelete);
    	
    	// Verify Contact to Delete does not exist
    	assertFalse(service.getContactBook().containsKey(idDelete));
    	
    	// Verify Contact to Keep still exists
    	assertTrue(service.getContactBook().containsKey(idKeep));
    }
}