//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  2/14/2026
//===============================================================================
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Added MethodOrderer as an easy way to organize the console output
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import main.Task;
import main.TaskService;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceTest {
	private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService();
    }
    
    /*
     *  Task Add Task Test
     */
    
    // Success: Create task and verify it is added to the Map.
    @Test
    @Order(1)
    void testSuccessAddTask() {
        service.addTask("Initial Task", "This is a valid description.");
        
        // Since IDs are random, verify it was added to the list
        assertFalse(service.getTaskList().isEmpty());
        assertEquals(1, service.getTaskList().size());
        
        // Get task ID
        Task task = service.getTaskList().values().iterator().next();
        String id = task.getTaskID();
        // Validate correct data was added
        assertTrue(service.getTaskList().containsKey(id));
		assertEquals("Initial Task", service.getTaskList().get(id).getTaskName());
		assertEquals("This is a valid description.", service.getTaskList().get(id).getTaskDescription()); 
    }
    
    // Success: Verify the generated ID is exactly 10 characters (check padding).
    @Test
    @Order(2)
    void testSuccessTaskIDFormat() {
    	 service.addTask("Test Task", "Test description.");
    	// Get task ID
         Task task = service.getTaskList().values().iterator().next();
         String id = task.getTaskID();
         
         assertEquals(10, id.length(), "Generated ID must be exactly 10 characters long.");
         assertTrue(id.matches("\\d{10}"), "Generated ID should be a 10-digit numeric string.");
    }
    
    // Fail: addTask with Name > 20 chars and Name = null.
    @Test
    @Order(3)
    void testFailAddTaskNameTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addTask("This Name is wayy too long", "Test description.");
    	});
    	
    }
    
    @Test
    @Order(4)
    void testFailAddTaskNameNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addTask(null, "Test description.");
    	});
    	
    }
    
    // Fail: addTask with null Description.
    @Test
    @Order(5)
    void testFailAddTaskDescTooLong() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addTask("Test Task", "You should know that this description is wayyy too long for this sorta program...");
    	});
    	
    }
    
    @Test
    @Order(6)
    void testFailAddTaskDescNull() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.addTask("Test Task", null);
    	});
    	
    }

    /*
     *  Task Unique ID Test
     */
    
    // Success: Add a high volume of tasks (e.g., 100) and verify the Map size is exactly 100.
    // 			Verify that the do-while loop prevents duplicate IDs from overwriting data.
    @Test
    @Order(7)
    void testIdUniqueness() {
    	int iteration = 100; //For 100 new tasks
    	
    	// Create 100 tasks
    	for (int i = 0; i < iteration; i++) {
    		service.addTask("Test Task" + i, "Generic task description.");
    	}
    	// If all IDs are unique, the size should equal the set iteration number
    	// else, some IDs are being overwritten :(((
    	assertEquals(iteration, service.getTaskList().size(), "The task list size should equal the iteration nummber");
    }

    
    /*
     *  Task Delete Test
     */
    
    // Success: Delete a task by a known ID and verify it is removed from the Map.
    @Test
    @Order(8)
    void testSuccessDeleteTask() {
    	// Get ID 
    	String id = service.addTask("Initial Task", "This is a valid description.");
        // Delete task
        service.deleteTask(id);
        
        // Assert the map is now empty
        assertTrue(service.getTaskList().isEmpty());
        // Verify ID was deleted
        assertFalse(service.getTaskList().containsKey(id));
    }
    
    // Success: Delete a task by a know ID with multiple tasks in the List
    @Test
    @Order(9)
    void testDeleteMultipleTask() {
    	// Create two tasks and store returned IDs
    	String idDelete = service.addTask("Task to Delete", "This task will be deleted.");
        String idKeep = service.addTask("Task to Keep", "This task will stay.");
        
        // Delete first task
        service.deleteTask(idDelete);
        
        // Verify Task to Delete does not exist
        assertFalse(service.getTaskList().containsKey(idDelete));
        
        // Verify Task to Keep still exists
        assertTrue(service.getTaskList().containsKey(idKeep));
    }
    
    // Fail: Attempt to delete an ID that does not exist (Verify IllegalArgumentException).
    @Test
    @Order(10)
    void testFailDeleteTaskUnknownID() {
    	Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.deleteTask("1234567890");
    	});
    	
    }
    
    /*
     *  Task Update Name Test
     */
    
    // Success: Update name of an existing ID and verify the change via getter.
    @Test
    @Order(11)
    void testSuccessUpdateTaskName() {
    	// Get id
    	String id = service.addTask("Terrible Task Name", "Remind Professor Kraya to have a wonderful day.");
        // Update task name
        service.updateTaskName(id, "Send a Note");
        
        //Verify name "Terrible Task Name" was updated to "Send a Note"
        assertEquals("Send a Note", service.getTaskList().get(id).getTaskName());
        
        // NOTE: The println was swallowed for me. Execute this one its own to see the success output. 
    }
    
    // Fail: Update name for an ID that does not exist.
    @Test
    @Order(12)
    void testFailUpdateTaskNameNonExist() {
        service.addTask("Okay Task Name", "Generic Task Description");
        // Attempt update with bad id
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updateTaskName("1234567890", "Best Task Name Ever");
    	});
    }
    
    // Fail: Update name with invalid length (> 20 chars).
    @Test
    @Order(13)
    void testFailUpdateTaskNameInvalidLength() {
    	String id = service.addTask("Okay Task Name", "Generic Task Description");
    	// Attempt update with bad name
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updateTaskName(id, "The Absolute Best Task Name Ever Heard Of!!!!!");
    	});
    }
    
    /*
     *  Task Update Description Test
     */
    
    // Success: Update description of an existing ID and verify the change via getter.
    @Test
    @Order(14)
    void testSuccessUpdateTaskDesc() {
    	// Get id
    	String id = service.addTask("Update Grade", "Remind Prof.Kraya to give hardworking students As.");
        // Update task description
        service.updateTaskDescription(id, "Please Prof.Kraya to give hardworking students As.");
        
        //Verify description "Remind" was updated to "Please"
        assertEquals("Please Prof.Kraya to give hardworking students As.", service.getTaskList().get(id).getTaskDescription());
        
        // NOTE: The println was swallowed for me. Execute this one its own to see the success output. 
    }
    
    // Fail: Update description for an ID that does not exist.
    @Test
    @Order(15)
    void testFailUpdateTaskDescNonExist() {
        service.addTask("Make Costing Report", "Create an excel document no one will ever look at.");
        // Attempt update with bad id
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updateTaskDescription("1234567890", "Create an excel document for my boss.");
    	});
    }
    
    // Fail: Update description with invalid length (> 50 chars).
    @Test
    @Order(16)
    void testFailUpdateTaskDescInvalidLength() {
    	// Get id
    	String id = service.addTask("Make Costing Report", "Create an excel document no one will ever look at.");
    	// Attempt update with bad description
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
    		service.updateTaskDescription(id, "Create an excel document for my boss because he says so or whatever.");
    	});
    }

}
