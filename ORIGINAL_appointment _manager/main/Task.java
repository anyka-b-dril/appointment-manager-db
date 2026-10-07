//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  1/27/2026
//===============================================================================

package main;

public class Task {
	
	// taskID shall not be updatable
	private final String taskID;
	private String taskName;
	private String taskDescription;
	
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
	
	/*
	 *  Constructor
	 */
	public Task(String taskID, String taskName, String taskDescription) {
		super();
		
		// The Task object shall have a required unique task ID String that cannot be longer than 10 characters.
		validateFieldLength("Task ID", taskID, 10);
		
		// The task object shall have a required name String field that cannot be longer than 20 characters.
		validateFieldLength("Task Name", taskName, 20);
		
		// The task object shall have a required description String field that cannot be longer than 50 characters.
		validateFieldLength("Task Description", taskDescription, 50);
		
		// If no exceptions are thrown; create task object
		this.taskID = taskID;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
	}
	
	
	/*
	 *  Get Functions
	 */
	
	// Get task ID 
	public String getTaskID() {
		return taskID;
	}
	
	// Get task name
	public String getTaskName() {
		return taskName;
	}
	
	// Get task description
	public String getTaskDescription() {
		return taskDescription;
	}
	
	/*
	 *  Set Functions
	 */
	
	// Update task name
	public void setTaskName(String newtaskName) {
		validateFieldLength("Task Name", newtaskName, 20);
		this.taskName = newtaskName;
	}
	
	// Update task description
	public void setTaskDescription(String newtaskDescription) {
		validateFieldLength("Task Description", newtaskDescription, 50);
		this.taskDescription = newtaskDescription;
	}
}
