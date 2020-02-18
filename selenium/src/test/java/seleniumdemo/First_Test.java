package seleniumdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class First_Test {
	private WebDriver driver;
	@Test
	public void LoginApplication() {
		driver.findElement(By.id("Email")).sendKeys("Gaurav.swaraj1987@gmail.com");
		driver.findElement(By.id("Password")).sendKeys("Ganpatiasdf@1990");
		driver.findElement(By.xpath("//input[@value='Log in']")).click();
		Assert.assertEquals("Manzoor", driver.getTitle());
	}
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "E:\\Selenium_Upgradation\\chromedriver_win32\\new\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://demowebshop.tricentis.com/login");
	}

	@AfterMethod
	public void jiraBug(ITestResult result) throws JiraException {
		if(result.getStatus()==ITestResult.FAILURE) {
			BasicCredentials creds = new BasicCredentials("mukhtarmehadi26","mukhtar@123");
			JiraClient jira= new JiraClient("http://localhost:7575",creds);
			Issue issuename = jira.createIssue("MAN", "Bug").field(Field.SUMMARY, result.getMethod().getMethodName()+"is failed due to : "+result.getThrowable().toString()).field(Field.DESCRIPTION, "Hello Manzoor").execute();
			System.out.println("issue is created in jira with issue key "+issuename.getKey());
		}
	}
	@AfterClass
	public void afterClass() {
		driver.close();
	}
}
