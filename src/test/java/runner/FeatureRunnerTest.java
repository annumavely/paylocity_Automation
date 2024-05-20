package runner;

import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utilityFiles.BaseClass;

@RunWith(Cucumber.class)
@CucumberOptions(
		tags="@api",
		features= {"features"},
		glue= {"stepDefinition"},
		plugin = {"summary","pretty", "html:target/execution-reports.html",
				  "json:target/reports"},
		monochrome =true
		   )
public class FeatureRunnerTest extends AbstractTestNGCucumberTests{

	
}
