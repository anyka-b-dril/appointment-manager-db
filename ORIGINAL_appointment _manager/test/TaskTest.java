//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/14/2026
//===============================================================================
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import main.Task;

class TaskTest {
	
	/*
	 *  Object Tests
	 */
	
	// Successful object creation (all valid fields) and object state matches constructor inputs
	@Test
	void testTaskClassSuccess() {
	    // Create a valid task using the limits: ID(10), Name(20), Desc(50)
	    Task task = new Task("1234567890", "Design Database", "Create the initial schema for the task service.");
	    
	    // Verify that the object was created and the data is stored correctly
	    assertAll("Task Constructor Validation",
	        () -> assertEquals("1234567890", task.getTaskID()),
	        () -> assertEquals("Design Database", task.getTaskName()),
	        () -> assertEquals("Create the initial schema for the task service.", task.getTaskDescription())
	    );
	}
	
	/*
	 *  Task ID Tests
	 */
	
	// Success: Exactly 10 characters
	@Test
	void testSuccessTaskIDExactlyTenCharacters() {
	    Task task = assertDoesNotThrow(() -> new Task("1234567890", "Valid Name", "Valid Description"),"Constructor should not throw exception for 10 character ID.");
	    
	    // Verify the state was correctly assigned
	    assertEquals("1234567890", task.getTaskID(), "Task ID should match the 10-character input.");
	}
	
	// Fail: 11 characters
	@Test
	void testFailCreationIdTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task("11234567890", "Design Database", "Create the initial schema for the task service.");
		}); 
	}
	
	// Fail: Null
	@Test
	void testFailCreationIdNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task(null, "Design Database", "Create the initial schema for the task service.");
		}); 
	}
	
	// Verify: No setter method exists
	@Test
	void testSuccessNoSetIdMethodExists() {
	    try {
	        Task.class.getMethod("setTaskID", String.class);
	        fail("An 'setTaskID' method was found, but the ID should not be updatable.");
	    } catch (NoSuchMethodException e) {
	        // Success: The method does not exist
	    }
	}
	
	/*
	 *  Task Name Tests
	 */
	
	// Success: Exactly 20 characters
	@Test
	void testSuccessTaskNameExactlyTwentyCharacters() {
	    Task task = assertDoesNotThrow(() -> new Task("1234567890", "Valid Ten Digit Name", "Valid Description"),"Constructor should not throw exception for 20 character Name.");
	    
	    // Verify the state was correctly assigned
	    assertEquals("Valid Ten Digit Name", task.getTaskName(), "Task Name should match the 20-character input.");
	}
	
	// Fail: 21 characters
	@Test
	void testFailTaskNameTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task("1234567890", "!Valid Ten Digit Name", "Create the initial schema for the task service.");
		}); 
	}
		
	// Fail: Null
	@Test
	void testFailTaskNameNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task("1234567890", null, "Create the initial schema for the task service.");
		}); 
	}
	
	// Success: setTaskName with valid string
	@Test
	void testSuccessTaskSetName() {
	    // Create a valid task using the limits: ID(10), Name(20), Desc(50)
	    Task task = new Task("1234567890", "Design Database", "Create the initial schema for the task service.");
	    task.setTaskName("Create Databases");
	    
	    // Verify that the object was created and the data is stored correctly
	    assertAll("Task Constructor Validation",
	        () -> assertEquals("1234567890", task.getTaskID()),
	        () -> assertEquals("Create Databases", task.getTaskName(), "Task name should be 'Create Databases'"),
	        () -> assertEquals("Create the initial schema for the task service.", task.getTaskDescription())
	    );
	}
	
	// Fail: setTaskName with invalid string (null or > 20 chars)
	@Test
	void testFailSetTaskNameTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Task task = new Task("1234567890", "Design Database", "Create the initial schema for the task service.");
			task.setTaskName("Create Databases With Owner Postgres");
			
		});
	}
	
	@Test
	void testFailSetTaskNameNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Task task = new Task("1234567890", "Design Database", "Create the initial schema for the task service.");
			task.setTaskName(null);
			
		});
	}
	
	/*
	 *  Task Description Tests
	 */
	
	// Success: Exactly 50 characters
	@Test
	void testSuccessTaskDescExactlyFiftyCharacters() {
	    Task task = assertDoesNotThrow(() -> new Task("1234567890", "Valid Name", "This description is exactly fifty characters long!"),"Constructor should not throw exception for 50 character Description.");
	    
	    // Verify the state was correctly assigned
	    assertEquals("This description is exactly fifty characters long!", task.getTaskDescription(), "Task Description should match the 50-character input.");
	}
	
	// Fail: 51 characters
	@Test
	void testFailTaskDescTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task("1234567890", "Valid Name", "This description was exactly fifty characters long!");
		}); 
	}
			
	// Fail: Null
	@Test
	void testFailTaskDescNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task("1234567890", "Valid Name", null);
		});
	}
		
	// Success: setTaskDescription with valid string
	@Test
	void testSuccessTaskSetDesc() {
	    // Create a valid task using the limits: ID(10), Name(20), Desc(50)
	    Task task = new Task("1234567890", "Design Database", "Create the initial schema for the task service.");
	    task.setTaskDescription("Apply schema changes and write code to handle SQL.");
	    
	    // Verify that the object was created and the data is stored correctly
	    assertAll("Task Constructor Validation",
	        () -> assertEquals("1234567890", task.getTaskID()),
	        () -> assertEquals("Design Database", task.getTaskName()),
	        () -> assertEquals("Apply schema changes and write code to handle SQL.", task.getTaskDescription(), "Task Description should include applying schema changes")
	    );
	}
		
	// Fail: setTaskDescription with invalid string (null or > 50 chars)
	@Test
	void testFailSetTaskDescTooLong() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Task task = new Task("1234567890", "Design Database", "Create the initial schema for the task service.");
			task.setTaskDescription("Write code to handle SQL or NoSQL queries, ensuring the schema works as expected.");
		});
	}
	
	@Test
	void testFailSetTaskDescNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Task task = new Task("1234567890", "Design Database", "Create the initial schema for the task service.");
			task.setTaskDescription(null);
		});
	}

}
