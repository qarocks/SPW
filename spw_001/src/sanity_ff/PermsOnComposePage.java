/*
*************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/08/2010 Created initial script
Purpose: To test permissions on Compose page
Desc: This script intends to test user permissions on the compose page
Scope: Testing for only permissions and successful sending of mails will be done. Real world Inbox checking etc. is not part of this test suite.
Requirements: 4 basic users with different permissions are needed to test.
sneha.qa.24@gmail.com: all perms
muunni.24@gmail.com: no perms
snehamtd001@gmail.com: only LFT
snehamtd002@yahoo.com : only SECURE
*/


package sanity_ff;



import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;




import com.thoughtworks.selenium.Selenium;

public class PermsOnComposePage {
	private WebDriver driver;
	
	private String baseUrl=Functions.baseUrl;
	private String composeUrl=Functions.compose_url;
	private String inboxUrl=Functions.inbox_url;
	private String sentUrl=Functions.sent_url;
	private String outboxUrl=Functions.outbox_url;
	
	Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	String user_allperms="sneha.qa.24@gmail.com",user_noperms="muunni.24@gmail.com",user_onlysecure="snehamtd002@yahoo.com",user_onlylft="snehamtd001@gmail.com";
	String pwd_allusers="123abc";
	String pathAutoItScript="C:\\Users\\Sneha\\Desktop\\automation files\\silver_autoit.exe";
	String pathAutoItScript_greaterthan25MB="C:\\Users\\Sneha\\Desktop\\automation files\\silver_autoit_greaterthan25mb.exe";
	WebElement ele=null;
	

	
	
	
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\6.0.2\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
	}

	
	
	
	@Test
	public void NonSecureNoattachs() throws Exception {
	
		System.out.println("Testing user with LFT perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--29");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--29");
		driver.switchTo().defaultContent();
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS:Permission checking passed for LFT user sending mail with no attachs and non-secure.");
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		System.out.println("Testing user with Secure perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(3000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		 		   
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--30");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--30");
		driver.switchTo().defaultContent();
       
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		
		 Runtime.getRuntime().exec(pathAutoItScript);
			
			Thread.sleep(3000);
			
			Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
			ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
			ele.click();
			Thread.sleep(2000);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
	//*********************************************************
	
		
		
		
		System.out.println("Testing user with ALL perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(3000); // time for illegal recipients to show up in red
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		 		   
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--31");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--31");
		driver.switchTo().defaultContent();
       
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		
		 Runtime.getRuntime().exec(pathAutoItScript);
			
			Thread.sleep(3000);
			
			Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
			ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
			ele.click();
			Thread.sleep(2000);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
	//*********************************************************
		
		
		System.out.println("Testing user with NO perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(3000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		 		   
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--32");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--32");
		driver.switchTo().defaultContent();
       
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		
		 Runtime.getRuntime().exec(pathAutoItScript);
			
			Thread.sleep(3000);
			
			Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
			ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
			ele.click();
			Thread.sleep(2000);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//******************************************************************
		
		
		
		
	}// end of Test
	
	@Test
	public void SecureNoattachs() throws Exception {
	
		
		System.out.println("Testing user with LFT perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--25");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--25");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
	
		
		
		System.out.println("Testing user with Secure perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--26");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--26");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with ALL perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--27");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--27");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
	
		
		System.out.println("Testing user with NO perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--28");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--28");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
	}// end of Test
	
	@Test
	public void NonSecureGreater25MB() throws Exception {
		
	
		System.out.println("Testing user with LFT perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--21");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--21");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
	
		
		
		
		
		System.out.println("Testing user with SECURE perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--22");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--22");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		
		
		

		System.out.println("Testing user with ALL perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--23");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--23");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		
		System.out.println("Testing user with NO perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--24");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC--24");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		
	
	}// end of Test
	
	@Test
	public void SecureLesser25MB() throws Exception {
		
		
		System.out.println("Testing user with LFT perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("secure")).click();
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--12");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-12");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
	
		
		System.out.println("Testing user with SECURE perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("secure")).click();
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--11");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-11");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
	
		
		
		System.out.println("Testing user with ALL perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("secure")).click();
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--10");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-10");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
	
		
		System.out.println("Testing user with NO perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("secure")).click();
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--09");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-09");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		
		
		
	}// end of Test
	
	@Test
	public void NonSecureLesser25MB() throws Exception {
		
	
		
		System.out.println("Testing user with LFT perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--16");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-16");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
	
	
		
		
		System.out.println("Testing user with SECURE perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--15");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-15");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
	
		
		
		
		
		System.out.println("Testing user with ALL perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--14");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-14");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		
		 Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--13");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-13");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		
	}// end of Test
	
	@Test
	public void SecureGreater25MB() throws Exception {
		
		
	
		System.out.println("Testing user with LFT perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--20");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-20");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************	
		
		
		
		
		System.out.println("Testing user with SECURE perms only");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--19");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-19");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************	
		
		
		
		System.out.println("Testing user with ALL perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--18");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-18");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************	
	
		
		System.out.println("Testing user with NO perms");
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		driver.findElement(By.id("secure")).click();
		 Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();

		Thread.sleep(3000);
		
		Functions.LftAndSecureErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("id_subject")).clear();
		driver.findElement(By.id("id_subject")).sendKeys("TC--17");
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']","TC-17");
		driver.switchTo().defaultContent();
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************	
	
	}// end of Test

	
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown

}// end of PermsOnComposePage class