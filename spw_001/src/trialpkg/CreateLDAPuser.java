package trialpkg;


import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.thoughtworks.selenium.Selenium;

public class CreateLDAPuser {
	private WebDriver driver;
	private String baseUrl="http://192.168.1.129";private WebDriverBackedSelenium wdb;Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	
	
	
	@Test
	public void CreateLdapMain() throws Exception {
		driver.get(baseUrl);
		System.out.println("Now navigating to http://192.168.1.129");
		System.out.println("Now logging in as admin");
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("admin");
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("abc123");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver.findElement(By.id("users")).click();

		driver.findElement(By.id("ldap")).click();
		assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("id_new_auth")).clear();
		driver.findElement(By.id("id_new_auth")).sendKeys("auth_001");
		driver.findElement(By.name("submit-new")).click();
		assertEquals("auth_001 LDAP Authentication Wizard", driver.findElement(By.id("heading")).getText());
		assertEquals("Which LDAP server name best fits your setup?",driver.findElement(By.cssSelector("h2")).getText());
		System.out.println("Testing step 1: Select server brand");
		WebElement select = driver.findElement(By.xpath("//*[@id='id_server_brand']"));
		List<WebElement> options = select.findElements(By.tagName("option"));
		   for (WebElement option : options) {
		 		    if ("Microsoft Active Directory".equalsIgnoreCase(option.getText())){
		     option.click();
		     
		     break;
		    }
		   }
		Thread.sleep(2000);
		   driver.findElement(By.id("submit-next")).click();
		   System.out.println("Testing step 2: Set LDAP record attribs");
		   assertEquals("Choose your LDAP attribute names (records' keys).", driver.findElement(By.cssSelector("h2")).getText());
		   System.out.println("Making sure that step 2 text boxes ahve the correct default values");
		   System.out.println(" "+driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
		   assertEquals("cn", driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
		   System.out.println(" "+driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
		  assertEquals("distinguishedName", driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
		   
			assertEquals("sAMAccountName", driver.findElement(By.id("id_username_attr")).getAttribute("value"));
			assertEquals("mail", driver.findElement(By.id("id_email_addr_attr")).getAttribute("value"));
			assertEquals("proxyAddresses", driver.findElement(By.id("id_alt_email_attr")).getAttribute("value"));
			driver.findElement(By.id("submit-next")).click();
			System.out.println("Testing step 3: Set Primary bind data");
			assertEquals("", driver.findElement(By.id("id_server")).getAttribute("value"));
			assertEquals("389", driver.findElement(By.id("id_port")).getAttribute("value"));
			assertEquals("on", driver.findElement(By.id("id_ssl_used")).getAttribute("value"));
			assertEquals("", driver.findElement(By.id("id_bind_pwd")).getAttribute("value"));
			assertEquals("30", driver.findElement(By.id("id_timeout")).getAttribute("value"));
			assertEquals("on", driver.findElement(By.id("id_active")).getAttribute("value"));
			
			driver.findElement(By.id("id_server")).clear();
			driver.findElement(By.id("id_server")).sendKeys("192.168.1.134");
			driver.findElement(By.id("id_bind_name")).clear();
			driver.findElement(By.id("id_bind_name")).sendKeys("TEST\\Administrator");
			driver.findElement(By.id("id_bind_pwd")).clear();
			driver.findElement(By.id("id_bind_pwd")).sendKeys("123Lutsen");
			driver.findElement(By.id("submit-next")).click();
			
			System.out.println("Testing Step 4: Compose search query");
			driver.findElement(By.id("id_base_dns")).clear();
			driver.findElement(By.id("id_base_dns")).sendKeys("dc=test,dc=lutsendata,dc=net");
			assertEquals("(|(objectClass=person)(objectClass=user))", driver.findElement(By.id("id_search_filter")).getAttribute("value"));
			assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
			assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
			driver.findElement(By.id("submit-next")).click();
			System.out.println("Testing step 5: User settings");
			assertEquals("Choose the settings that will apply to LDAP users when they are first retrieved from the LDAP server.", driver.findElement(By.cssSelector("h2")).getText());
			assertEquals("on", driver.findElement(By.id("id_delivery_notify")).getAttribute("value"));
			assertEquals("domain user", driver.findElement(By.linkText("domain user")).getText());
			driver.findElement(By.id("submit-finish")).click();
			selenium.waitForPageToLoad("3000");
			assertEquals("The wizard was successfully completed and the \"auth_001\" object was created.", driver.findElement(By.cssSelector("li.success")).getText());
			System.out.println("LDAP authenticator created successfully");
			System.out.println("Creating a clone");
			
			driver.findElement(By.cssSelector("img[alt=\"Copy\"]")).click();
			driver.findElement(By.id("id_new_clone")).clear();
			driver.findElement(By.id("id_new_clone")).sendKeys("auth_001_clone");
			driver.findElement(By.xpath("//button[@type='button']")).click();
			driver.findElement(By.cssSelector("#auth_001_clone > td")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
			assertEquals("auth_001_clone", driver.findElement(By.cssSelector("#auth_001_clone > td")).getText());
			System.out.println("Clone successfully created!");
			
			
			
			System.out.println("Testing health test for authenticator created");
			
			
			driver.findElement(By.linkText("Home")).click();
			driver.findElement(By.id("reports")).click();
			driver.findElement(By.id("health")).click();
			assertEquals("Auth_001", driver.findElement(By.linkText("Auth_001")).getText());
			driver.findElement(By.linkText("Auth_001")).click();
			driver.findElement(By.id("showDetailsLink")).click();
			driver.findElement(By.id("startTest")).click();
			Thread.sleep(2000);
			assertEquals("The host 192.168.1.134 and port 389 were reached successfully.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li/div[2]")).getText());
			assertEquals("Successfully logged in (binded) to the LDAP server with username \"TEST\\Administrator\" and the password provided.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[2]/div[2]")).getText());
			System.out.println(" "+driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText());
			if(driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText().contains("The LDAP search found"))
				
				assertEquals("true","true");
			else
				assertEquals("false","true");
			
		
			assertEquals("No users were missing usernames in the LDAP search results.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[4]/div[2]")).getText());
			System.out.println("Health test successfull!");
			
			System.out.println("Deleting authenticator and clone");
			
			driver.findElement(By.linkText("Home")).click();
			driver.findElement(By.id("users")).click();
			driver.findElement(By.id("ldap")).click();
			driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
			driver.switchTo().alert().accept();
			Thread.sleep(2000);
			
			// ERROR: Caught exception [ERROR: Unsupported command [getConfirmation]]
			assertEquals("the LDAP authenticator auth_001_clone was deleted", driver.findElement(By.cssSelector("li.success")).getText());
			driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
			driver.switchTo().alert().accept();
			Thread.sleep(2000);
			// ERROR: Caught exception [ERROR: Unsupported command [getConfirmation]]
			assertEquals("the LDAP authenticator auth_001 was deleted", driver.findElement(By.cssSelector("li.success")).getText());
			
			System.out.println("Authenticator and its clone successfully deleted!");
			
		   
		   
	}// end of @test

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
