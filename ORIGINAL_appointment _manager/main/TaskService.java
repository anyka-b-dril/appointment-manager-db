//===============================================================================
// Author: Anyka Perzynski-Drilling
// Class: CS-320
// Date:  1/27/2026
//===============================================================================

package main;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TaskService {
	// Task list with ID as the key
	private Map<String, Task> taskList = new HashMap<>();
	// Initialize random integer for unique task ID
	private final Random random = new Random();
	
	/*
	 *  Add task with a unique ID
	 */
	public String addTask(String taskName, String taskDescription) {
		int rawTaskID;
		String taskID;
		
		// Validate current random generated id is not in use
		// Otherwise, we risk overwriting data
		do {
			rawTaskID = random.nextInt(Integer.MAX_VALUE); // Generate a number between 0-2.1 billion (all are 10 digits or fewer)
			// Format it to be exactly 10 digits with leading zeros
			taskID = String.format("%010d", rawTaskID);
		} while (taskList.containsKey(taskID));
		
		// Create new task object
		Task newTask = new Task(taskID, taskName, taskDescription);
		// Add task to task list
		taskList.put(taskID, newTask);
				
		// Celebrate success
		System.out.println("Created new task: ID: " + taskID + ", Name: " + taskName + ", Description: " + taskDescription);
		
		// Return the task ID so the application knows what was created
		return taskID;
	}
	
	/*
	 *  Delete task by unique ID
	 */
	public void deleteTask(String taskID) {
		if (taskList.containsKey(taskID)) {
			// Record deleted items
			Task removed = taskList.remove(taskID);
			// Report deletion
			System.out.println("Deleted task: ID: " + removed.getTaskID() + ", Name: " + removed.getTaskName());
		}
		else {
			// Report error
			throw new IllegalArgumentException("Error: ID " + taskID + " not found.");
		}
		
	}
	
	/*
	 *  Update task by unique ID
	 */
	
	// Task Name
	public void updateTaskName(String taskID, String newName) {
		// Create temporary reference
		Task temp = taskList.get(taskID);
		
		// If an id match was found, update taskName with newName
		if (temp != null) {
			temp.setTaskName(newName);
			// Report success
			System.out.println("Task name updated for ID: " + taskID);
		}
		else {
			throw new IllegalArgumentException("Error: ID " + taskID + " not found.");
		}
	}
	
	// Description
	public void updateTaskDescription(String taskID, String newDescription) {
		// Create temporary reference
		Task temp = taskList.get(taskID);
		
		// If an id match was found, update taskDescription with newDescription
		if (temp != null) {
			temp.setTaskDescription(newDescription);
			// Report success
			System.out.println("Task description updated for ID: " + taskID);
		}
		else {
			throw new IllegalArgumentException("Error: ID " + taskID + " not found.");
		}
	}
		
	/*
	 *  Get contents
	 */
	public Map<String, Task> getTaskList() {
	    return taskList;
	}
}
