package trialpkg;


import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.thoughtworks.selenium.Selenium;

public class CreateLocalUser {
	private WebDriver driver;
	private String baseUrl="http://192.168.1.130";private WebDriverBackedSelenium wdb;Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	
	
	
	@Test
	public void CreateLocalMain() throws Exception {
		driver.get(baseUrl);
		System.out.println("Now navigating to http://192.168.1.130");
		System.out.println("Now logging in as admin");
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("admin");
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("abc123");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("local")).click();
		driver.findElement(By.id("id_new_user")).clear();
		driver.findElement(By.id("id_new_user")).sendKeys("new_user@gmail.com");
		driver.findElement(By.name("submit-new")).click();
		selenium.waitForPageToLoad("3000");
		System.out.println("The page title is:"+driver.getTitle());
		assertEquals("Edit the Local User new_user@gmail.com", driver.getTitle());
		Functions.MyWaitfunc(driver,"//*[@id='id_username']");
		
		System.out.println("username field has value: "+driver.findElement(By.id("id_username")).getAttribute("value"));
		assertEquals("new_user@gmail.com",driver.findElement(By.id("id_username")).getAttribute("value"));
		
		System.out.println("Default permission for local user is: "+driver.findElement(By.linkText("domain user")).getText());
		assertEquals("domain user", driver.findElement(By.linkText("domain user")).getText());
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.id("id_verify_password")).clear();
		driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("new_user@gmail.com");
		driver.findElement(By.id("id_secondary_emails")).clear();
		driver.findElement(By.id("id_secondary_emails")).sendKeys("secondary@gmail.com");
		
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
		driver.findElement(By.name("submit-save")).click();
		assertEquals("Settings for the local user new_user@gmail.com were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("The local user was created successfully!!");
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
