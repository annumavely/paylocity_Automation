#Author: annumavely@gmail.com
@api
Feature: Payload Benefits API validation
  To verify the user able to perform add, edit, and delete from API.
	
	@ap1
  Scenario: Get Employee list
  Given an Employer submit get employee list api request
  Then validate the response code
  And display no of records displayed
  
	@ap1
  Scenario: Add Employee
  Given an Employer add firstname as 'Sasi',lastname as 'Kuttan' and no of dependants as '0' in the request and submit
  Then validate the response code
  And validate response
  
  @ap1
  Scenario: Get Employee
	Given an Employer submit get employee api request to get employee details
	Then validate the response code
  And validate response obtained for get api
	
	@ap1
  Scenario: Update Employee
  Given an Employer update firstname as 'Sasimon',lastname as 'Kuttan' and no of dependants as '1' in the request and submit
  Then validate the response code
  And validate response obtained for update api
  
  @ap1
  Scenario: Delete Employee
  Given an Employer submit delete employee api request to delete employee details
  Then validate the response code