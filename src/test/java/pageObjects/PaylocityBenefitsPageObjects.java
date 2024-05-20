package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaylocityBenefitsPageObjects {

	public PaylocityBenefitsPageObjects(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	public By addButton = By.id("add");
	
	public String employeeTableData = "//table[@id='employeesTable']//following::td[columnNO]";
	public String editEmployee = "//td[text()=EMPLOYEEID]//parent::tr//child::i[@class='fas fa-edit']";
	public String deleteEmployee = "//td[text()=EMPLOYEEID]//parent::tr//child::i[@class='fas fa-times']";
			
	@FindBy(id ="Username")
	public WebElement userName;
	
	@FindBy(id="Password")
	public WebElement password;
	
	@FindBy(xpath="//button[text()='Log In']")
	public WebElement login;
	
	@FindBy(id="add")
	public WebElement btn_AddEmployee;
	
//	@FindBy(xpath="//i[@class='fas fa-edit']")
//	public List<WebElement> btn_EditEmployee;
//	
//	@FindBy(xpath="//i[@class='fas fa-times']")
//	public List<WebElement> btn_DeleteEmployee;
	
	@FindBy(xpath="//h5[text()='Add Employee']")
	public WebElement screen_AddEmployee_title;
	
	@FindBy(xpath="//h5[text()='Delete Employee']")
	public WebElement screen_DeleteEmployee_title;
	
	@FindBy(id="firstName")
	public WebElement screen_AddEmployee_FirstName;
	
	@FindBy(id="lastName")
	public WebElement screen_AddEmployee_LastName;
	
	@FindBy(id="dependants")
	public WebElement screen_AddEmployee_Dependents;
	
	@FindBy(xpath="//label[text()='First Name:']")
	public WebElement screen_AddEmployee_FirstName_label;
	
	@FindBy(xpath="//label[text()='Last Name:']")
	public WebElement screen_AddEmployee_LastName_label;
	
	@FindBy(xpath="//label[text()='Dependents:']")
	public WebElement screen_AddEmployee_Dependents_label;
	
	@FindBy(id="addEmployee")
	public WebElement screen_AddEmployee_Add_btn;
	
	@FindBy(id="updateEmployee")
	public WebElement screen_EditEmployee_Update_btn;
	
	@FindBy(xpath="//button[@id='addEmployee']//following-sibling::button[text()='Cancel']")
	public WebElement screen_Cancel_btn;
	
	@FindBy(xpath="//table[@id='employeesTable']//following::tr")
	public List<WebElement> employeeTableDetails;
	
	@FindBy(xpath="//div[contains(text(),'Delete employee record for')]")
	public WebElement screen_DeleteEmployee_text;
	
	@FindBy(id="addEmployee")
	public WebElement screen_DeleteEmployee_Delete_btn;
	
	@FindBy(xpath="//button[@id='deleteEmployee']//following-sibling::button[text()='Cancel']")
	public WebElement screen_DeteletEmployee_Cancel_btn;
}
 