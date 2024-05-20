package stepDefinition;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageActions.PaylocityLoginActions;
import utilityFiles.BaseClass;

public class PaylocityBenefits {

	PaylocityLoginActions paylocityPageActions = new PaylocityLoginActions();
	Scenario scenario;
	
	
	@Before("@ui")
	public static void setup() {
		BaseClass.setUpDriver();
	}
	
	
	@After("@ui")
	public void tearDown(Scenario scenario) {
		if(scenario.isFailed()) {
			final byte[] screenshot= ((TakesScreenshot)BaseClass.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "ïmage/png", scenario.getName());
		}
		BaseClass.tearDown();
	}
	
	
	@Given("^an Employer$")
	public void an_employer() {
		paylocityPageActions.launchApplication();
		paylocityPageActions.login();
	}

	
	@And("I am on the Benefits Dashboard page")
	public void i_am_on_the_benefits_dashboard_page() {
		paylocityPageActions.verifyPageTitle("Employees - Paylocity Benefits Dashboard");
		paylocityPageActions.verifyAddEmployeeButton();
	}
	
	@When("I select Add Employee")
	public void i_select_add_employee() {
		paylocityPageActions.selectButton("Add Employee");
	}
	@Then("I should be able to enter employee details '(.*?)','(.*?)','(.*?)'$")
	public void i_should_be_able_to_enter_employee_details(String strFName, String strLName, String strDependant) {
		paylocityPageActions.verifyAddEmployeeDisplayed("Add Employee");
		paylocityPageActions.enterEmployeeDetails(strFName,strLName,strDependant);
	}
	@Then("the employee should save")
	public void the_employee_should_save() {
		paylocityPageActions.saveEmployeeDetails("Add Employee");
	}
	@Then("I should see the employee in the table")
	public void i_should_see_the_employee_in_the_table() {
		paylocityPageActions.verifyEmployeeDetailsSaved();
	}
	@Then("the benefit cost calculations are correct")
	public void the_benefit_cost_calculations_are_correct() {
		paylocityPageActions.verifyBenefitCostCalculations();
	}
	
	@When("I select the Action Edit")
	public void i_select_the_action_edit() {
		paylocityPageActions.selectButton("Edit Employee");
	}

	@Then("I can edit employee details '(.*?)','(.*?)','(.*?)'$")
	public void i_can_edit_employee_details(String strFName, String strLName, String strDependant) {
		paylocityPageActions.verifyAddEmployeeDisplayed("Add Employee");
		paylocityPageActions.enterEmployeeDetails(strFName,strLName,strDependant);
		paylocityPageActions.saveEmployeeDetails("Edit Employee");
	}

	@And("the data should change in the table")
	public void the_data_should_change_in_the_table() {
		paylocityPageActions.verifyEditedDetailsSaved();
	}
	
	@When("I click the Action X")
	public void i_click_the_action_x() {
		paylocityPageActions.selectButton("Delete Employee");
	}

	@Then("the employee should be deleted")
	public void the_employee_should_be_deleted() {
		paylocityPageActions.verifyDeleteScreenDisplayedAndDetailsDetails("Delete Employee");  
	}
	
}
