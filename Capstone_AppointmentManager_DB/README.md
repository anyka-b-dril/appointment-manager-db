# Appointment Manager
***
### Prerequisites
- Required
	- MongoDB
		- [Download MongoDB Community Edition](https://www.mongodb.com/products/self-managed/community-edition)

- Optional
	- MongoDB Compass
		- [Download MongoDB Compass](https://www.mongodb.com/products/tools/compass)
		
### Operating Instructions

1. Start MongoDB Community Server on port 27017 (default)
	<ul>
		<li> On Windows: </li>
			<ul>
				<li>Check Services to ensure MongoDB Server is running</li>
				<li>If not already running, enter ``net start MongoDB``</li>
			</ul>
		<li> On Linux: </li>
			<ul>
				<li>Run ``sudo systemctl start mongod``</li>
			</ul>
	</ul>

2. (Optional) Run project as ``Maven Verify`` to observe integration tests

3. (Optional) Run project as ``Maven Test`` to observe JUnit tests

4. Run  _Main.java_  as a Java application

5. Use the terminal menu to add, read, update, or delete appointments

6. (Optional) Open MongoDB Compass and connect to 'mongodb://localhost:27017' to view database information.