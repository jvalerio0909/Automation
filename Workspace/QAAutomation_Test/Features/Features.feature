Feature: Test Users

Scenario: Do login in the application
	Given Open the browser and start application
	When I enter a username and a password
	Then the user should do login successfully
	
Scenario: Create new user
	Given I do click in create a new user
	When I enter information in the new user textfields
	Then Verify confirmation message
	
Scenario: Validate user added in table
	Given I do click in the employess tab
	When I Search a user in the table and I found it
	Then the application return and validate the user information
	
Scenario: Delete an user in the table
	Given I do click in the employess tab
	When I Search a user in the table and I found it
	Then I delete it
	
Scenario: Validate user deleted
	Given I do click in the employess tab
	When I Search a user in the table and I didn't find it
	Then I close the browser