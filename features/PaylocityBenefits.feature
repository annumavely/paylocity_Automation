#Author: annumavely@gmail.com
@test
Feature: Payload Benefits Dashboard page
  To verify the user able to perform add, edit, and delete employees and their dependents.
	
	Background:
	Given an Employer
	And I am on the Benefits Dashboard page

  @ui @test
  Scenario Outline: Add Employee
  When I select Add Employee
  Then I should be able to enter employee details '<firstname>','<lastname>','<dependant>'
  And the employee should save
  And I should see the employee in the table
  And the benefit cost calculations are correct
  Examples:
  |firstname|lastname|dependant|
  |SASIMONE |  Paul  |     1   |
  
  @ui @test
  Scenario: Edit Employee
	When I select the Action Edit
	Then I can edit employee details '<firstname>','<lastname>','<dependant>'
	And the data should change in the table
	Examples:
  |firstname|lastname|dependant|
  |SASI     |  Kuttan|     1   |
  
	@ui @test
  Scenario: Delete Employee
	When I click the Action X
	Then the employee should be deleted