/*
*************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/08/2010 Created initial script
Purpose: To test permissions on Forward page
Desc: This script intends to test user permissions on the forward page
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

public class PermsOnForwardPage {
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
	String emailBody,subject,emailId;
	
	
	
	
	
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
		subject="TC--33";
		emailBody="TC--33";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
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
		System.out.println("Testing user with SECURE perms only");
		subject="TC--34";
		emailBody="TC--34";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
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
		
		
		System.out.println("Testing user with ALL perms");
		subject="TC--35";
		emailBody="TC--35";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
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
		
		System.out.println("Testing user with NO perms");
		subject="TC--36";
		emailBody="TC--36";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
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
		
		
		
	}// end of Test
	
	@Test
	public void SecureNoattachs() throws Exception {

		System.out.println("Testing user with LFT perms only");
		subject="TC--37";
		emailBody="TC--37";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		System.out.println("Testing user with SECURE perms only");
		subject="TC--38";
		emailBody="TC--38";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with ALL perms");
		subject="TC--39";
		emailBody="TC--39";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--40";
		emailBody="TC--40";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		
	}// end of Test
	
	@Test
	public void NonSecureGreater25MB() throws Exception {
		
	
		System.out.println("Testing user with LFT perms only");
		subject="TC--41";
		emailBody="TC--41";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--42";
		emailBody="TC--42";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with ALL perms");
		subject="TC--43";
		emailBody="TC--43";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--44";
		emailBody="TC--44";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
	}// end of Test
	
	@Test
	public void SecureLesser25MB() throws Exception {
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--45";
		emailBody="TC--45";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--46";
		emailBody="TC--46";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with ALL perms");
		subject="TC--47";
		emailBody="TC--47";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--48";
		emailBody="TC--48";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		
	}// end of Test
	
	@Test
	public void NonSecureLesser25MB() throws Exception {
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--49";
		emailBody="TC--49";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--50";
		emailBody="TC--50";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
		System.out.println("Testing user with ALL perms only");
		subject="TC--51";
		emailBody="TC--51";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with No perms");
		subject="TC--52";
		emailBody="TC--52";
		
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
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
		
		
		// ************************************************************************
		
		
	}// end of Test
	
	@Test
	public void SecureGreater25MB() throws Exception {
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--53";
		emailBody="TC--53";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--54";
		emailBody="TC--54";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with ALL perms");
		subject="TC--55";
		emailBody="TC--55";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--56";
		emailBody="TC--56";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
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
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
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