/*
*************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/08/2010 Created initial script
Purpose: To test permissions on Reply To All page
Desc: This script intends to test user permissions on the Reply to all page
Scope: Testing for only permissions and successful sending of mails will be done. Real world Inbox checking etc. is not part of this test suite.
Requirements: 4 basic users with different permissions are needed to test.
sneha.qa.24@gmail.com: all perms
muunni.24@gmail.com: no perms
snehamtd001@gmail.com: only LFT
snehamtd002@yahoo.com : only SECURE
*/


package sanity_ff.backup;



import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;




import com.thoughtworks.selenium.Selenium;

public class PermsOnReplyToAllPage {
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
	String pathAutoItScript_lesserthan25mb="C:\\Users\\Sneha\\Desktop\\automation files\\silver_autoit.exe";
	String pathAutoItScript_greaterthan25MB="C:\\Users\\Sneha\\Desktop\\automation files\\silver_autoit_greaterthan25mb.exe";
	WebElement ele=null;String subject,emailBody;
	String emailId;
	
	
	
	
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
		subject="TC--81";
		emailBody="TC--81";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--82";
		emailBody="TC--82";
		Functions.LFTSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		//*************************************************************

		
		System.out.println("Testing user with ALL perms");
		subject="TC--83";
		emailBody="TC--83";
		Functions.LFTSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		//*************************************************************
		
		
		
		System.out.println("Testing user with NO perms");
		subject="TC--84";
		emailBody="TC--84";
		Functions.LFTSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		//*************************************************************
		
		
	}// end of NonSecureNoattachs
	
	@Test
	public void SecureNoattachs() throws Exception {
		
		
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--85";
		emailBody="TC--85";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
	
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--86";
		emailBody="TC--86";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		System.out.println("Testing user with ALL perms");
		subject="TC--87";
		emailBody="TC--87";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		System.out.println("Testing user with NO perms");
		subject="TC--88";
		emailBody="TC--88";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
	}//end of SecureNoattachs
	
	@Test
	public void NonSecureGreaterThan25mb() throws Exception {
		
		
		
		
		
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--89";
		emailBody="TC--89";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		
        Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--90";
		emailBody="TC--90";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
         Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		System.out.println("Testing user with ALL perms");
		subject="TC--91";
		emailBody="TC--91";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--92";
		emailBody="TC--92";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
	}//end of NonSecureGreaterThan25mb
	
	
	@Test
	public void SecureLesserThan25mb() throws Exception {
	
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--93";
		emailBody="TC--93";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--94";
		emailBody="TC--94";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		System.out.println("Testing user with ALL perms");
		subject="TC--95";
		emailBody="TC--95";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--96";
		emailBody="TC--96";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
	}//  end of SecureLesserThan25mb
	
	
	@Test
	public void NonSecureLesserThan25mb() throws Exception {
		
		
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--97";
		emailBody="TC--97";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		
		System.out.println("Testing user with secure perms only");
		subject="TC--98";
		emailBody="TC--98";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		System.out.println("Testing user with ALL perms");
		subject="TC--99";
		emailBody="TC--99";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		System.out.println("Testing user with NO perms");
		subject="TC--100";
		emailBody="TC--100";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		
        Runtime.getRuntime().exec(pathAutoItScript_lesserthan25mb);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
	}// end of NonSecureLesserThan25mb
	
	
	@Test
	public void SecureGreaterThan25mb() throws Exception {
		
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--101";
		emailBody="TC--101";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--102";
		emailBody="TC--102";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		System.out.println("Testing user with ALL perms");
		subject="TC--103";
		emailBody="TC--103";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		
		
		
		System.out.println("Testing user with NO perms");
		subject="TC--104";
		emailBody="TC--104";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);
		
		driver.get(baseUrl+"/transfer/email/view/"+emailId);
		
		assertEquals("Reply to All", driver.findElement(By.id("reply-all")).getText());
		driver.findElement(By.id("reply-all")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//System.out.println(""+driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked"));
		assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
        Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.LftAndSecureErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		//***********************************************************************************
		
		
		
	}// end of SecureGreaterThan25mb
	
	

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown
	
	
	
	}// end of PermsOnReplyPage class