package sanity_ff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebElement.*;
import java.util.List;

import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.By;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class Functions{
	

public static void MyWaitfunc(WebDriver driver,String element) throws Exception{
		
		for (int second = 0;; second++) {
			if (second >= 60) {fail("timeout");}
			try { if (driver.findElement(By.xpath(element)).isDisplayed()) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}	
		
	}

public static void WaitForUpload(WebDriver driver) throws Exception{
	
	for (int second = 0;; second++) {
		if (second >= 120) {fail("timeout");}
		try { 
			if(driver.findElement(By.cssSelector("span.plupload_total_status")).getText().equalsIgnoreCase("100%"))
		       {Thread.sleep(2000);break;}
		
		else
		{continue;}
		
		} 
		
		catch (Exception e) {}
		Thread.sleep(1000);
	}	
	
}

public static boolean doesWebElementExist(WebDriver driver, By selector) 
{ 
       try { 
               driver.findElement(selector); 
           return true; 
       } catch (NoSuchElementException e) { 
           return false; 
       } 
}   



public static void SecureSend(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	driver.findElement(By.linkText("Compose")).click();
	selenium.waitForPageToLoad("3000");
    driver.findElement(By.id("secure")).click();
    driver.findElement(By.id("addrin")).sendKeys(recipient);
	driver.findElement(By.id("id_subject")).sendKeys(subject);			
	driver.findElement(By.id("addrsubmit")).click();

	driver.switchTo().frame("id_body_ifr");
			
	selenium.typeKeys("//body[@id='tinymce']", emailBody);
	driver.switchTo().defaultContent();
			
	driver.findElement(By.id("submitter")).click();
		
			
    String success_str_xpath="//html/body/div/div[2]/div[3]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Successfully sent the email")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();
     
}//end of secureSend method

public static void SecureSendLessthan25MB(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	driver.findElement(By.linkText("Compose")).click();
	selenium.waitForPageToLoad("3000");
    driver.findElement(By.id("secure")).click();
    driver.findElement(By.id("addrin")).sendKeys(recipient);
	driver.findElement(By.id("id_subject")).sendKeys(subject);			
	driver.findElement(By.id("addrsubmit")).click();

	driver.switchTo().frame("id_body_ifr");
			
	selenium.typeKeys("//body[@id='tinymce']", emailBody);
	driver.switchTo().defaultContent();
	Runtime.getRuntime().exec("C:\\Users\\Sneha\\Desktop\\silver_autoit.exe");
	
	
	
	Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
	WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
	Thread.sleep(2000);
	ele.click();
	
	
	Thread.sleep(3000);
			
	driver.findElement(By.id("submitter")).click();
		
			
    String success_str_xpath="//html/body/div/div[2]/div[3]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Successfully sent the email")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();
     
}//end of secureSendlessthan25MB method

public static void SecureSendGreaterthan25MB(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	driver.findElement(By.linkText("Compose")).click();
	selenium.waitForPageToLoad("3000");
    driver.findElement(By.id("secure")).click();
    driver.findElement(By.id("addrin")).sendKeys(recipient);
	driver.findElement(By.id("id_subject")).sendKeys(subject);			
	driver.findElement(By.id("addrsubmit")).click();

	driver.switchTo().frame("id_body_ifr");
			
	selenium.typeKeys("//body[@id='tinymce']", emailBody);
	driver.switchTo().defaultContent();
	Runtime.getRuntime().exec("C:\\Users\\Sneha\\Desktop\\silver_autoit_greaterthan25mb.exe");
	
	
	
	Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
	WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
	Thread.sleep(2000);
	ele.click();
	
	
	Thread.sleep(3000);
			
	driver.findElement(By.id("submitter")).click();
		
			
    String success_str_xpath="//html/body/div/div[2]/div[3]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Successfully sent the email")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();
     
}//end of secureSendGreaterthan25MB method




public static String FindIdwithSubject(WebDriver driver,Selenium selenium,String subject)
{
	  int flag=0;
	
	String EmailId=null;
	WebElement select = driver.findElement(By.xpath("//*[@id='EmailInbox']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   System.out.println("i was here!");
				   System.out.println(""+sub_sub_sub_option.getText());
				   
				   if(sub_sub_sub_option.getText().contains(subject))
					   
				   {
					   EmailId=sub_sub_option.getAttribute("id");
					   flag=1;break;
					   
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			   if(flag==1) break; 
		   }
		   if(flag==1) break;
		   }
		   
	   
	
	return EmailId;
	
	


}// end of FindIdwithSubject



public static String DraftsFindIdwithSubject(WebDriver driver,Selenium selenium,String subject)
{
	  int flag=0;
	
	String EmailId=null;
	WebElement select = driver.findElement(By.xpath("//*[@id='EmailDrafts']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   System.out.println("i was here!");
				   System.out.println(""+sub_sub_sub_option.getText());
				   
				   if(sub_sub_sub_option.getText().contains(subject))
					   
				   {
					   EmailId=sub_sub_option.getAttribute("id");
					   flag=1;break;
					   
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			   if(flag==1) break; 
		   }
		   if(flag==1) break;
		   }
		   
	   
	
	return EmailId;
	
	


}// end of FindIdwithSubject




public static void LFTSend(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.findElement(By.linkText("Compose")).click();
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec("C:\\Users\\Sneha\\Desktop\\silver_autoit.exe");
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[3]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Successfully sent the email")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

}//end of LFTsend method



public static void LFTSendGreaterThan25mb(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.findElement(By.linkText("Compose")).click();
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec("C:\\Users\\Sneha\\Desktop\\silver_autoit_greaterthan25mb.exe");
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[3]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Successfully sent the email")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

}//end of LFTSendGreaterThan25mb method










public static void CreateLocalUser(WebDriver driver,String baseUrl,Selenium selenium,String adminPwd,String localUsername,String localUserPwd)throws Exception
{
	
	driver.get(baseUrl);
	System.out.println("Now navigating to "+baseUrl);
	//driver.switchTo().window(driver.getWindowHandle());
	System.out.println("Now logging in as admin");
	Functions.MyWaitfunc(driver, "//*[@id='id_username']");
	driver.findElement(By.id("id_username")).sendKeys("\n");
	driver.findElement(By.id("id_username")).sendKeys("admin");
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys(adminPwd);
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	
	driver.findElement(By.id("users")).click();
	driver.findElement(By.id("local")).click();
	driver.findElement(By.id("id_new_user")).clear();
	driver.findElement(By.id("id_new_user")).sendKeys(localUsername);
	driver.findElement(By.name("submit-new")).click();
	selenium.waitForPageToLoad("3000");
	System.out.println("The page title is:"+driver.getTitle());
	assertEquals("Edit the Local User "+localUsername, driver.getTitle());
	Functions.MyWaitfunc(driver,"//*[@id='id_username']");
	
	System.out.println("username field has value: "+driver.findElement(By.id("id_username")).getAttribute("value"));
	assertEquals(localUsername,driver.findElement(By.id("id_username")).getAttribute("value"));
	
	System.out.println("Default permission for local user is: "+driver.findElement(By.linkText("domain user")).getText());
	assertEquals("domain user", driver.findElement(By.linkText("domain user")).getText());
	
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys(localUserPwd);
	driver.findElement(By.id("id_verify_password")).clear();
	driver.findElement(By.id("id_verify_password")).sendKeys(localUserPwd);
	driver.findElement(By.id("id_full_name")).clear();
	driver.findElement(By.id("id_full_name")).sendKeys(localUsername);
	driver.findElement(By.id("id_secondary_emails")).clear();
	driver.findElement(By.id("id_secondary_emails")).sendKeys("secondary@gmail.com");
	
	
	driver.findElement(By.name("submit-save")).click();
	assertEquals("Settings for the local user "+localUsername+" were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
	System.out.println("The local user was created successfully!!");
	
	driver.findElement(By.id("logout")).click();

}// end of createLocalUser method



public static void TestLDAPauth(WebDriver driver,Selenium selenium,String baseUrl)throws Exception{
	//This is a self-clean script where ldap authenticators are created , tested on and then deleted
	
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
		
		driver.findElement(By.id("logout")).click();
	
}//end of CreateLDAPUser method


public static void LftErrorMssg(WebDriver driver,String user)
{
	WebElement toDelete = null;
	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {   
				  if(sub_sub_option.getAttribute("info")!=null)
				   {
					  
				   if(sub_sub_option.getAttribute("info").contains("This email is considered a file transfer.  Since you do not have a file transfer license, your recipients are required to."))
				   
				   { System.out.println(""+sub_sub_option.getAttribute("info"));
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);assertEquals("true","false");}
				   }
				   
				   else
				   {
					   toDelete=sub_sub_option;
					   
					   
				  }  
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for
	 	
	   toDelete.click();	   
	   System.out.println("Removed user "+user+" from recipient list");
	   
	
	
}// end of LftErrorMssg

public static void SecureErrorMssg(WebDriver driver,String user)
{
	WebElement toDelete=null;
	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {
				   if(sub_sub_option.getAttribute("info")!=null)
				   {
				   if(sub_sub_option.getAttribute("info").contains("This email is considered a secure email.  Since you do not have a secure email license, your recipients are required to."))
				   
				   { System.out.println(""+sub_sub_option.getAttribute("info"));
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);System.out.println(""+sub_sub_option.getAttribute("info"));assertEquals("true","false");}
				   }
				   
				   else
				   {
					   toDelete=sub_sub_option;
					   
					   
				  }  
				   
				   
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for	
	   
	   toDelete.click();	   
	   System.out.println("Removed user "+user+" from recipient list");
}// end of SecureErrorMssg


public static void LftAndSecureErrorMssg(WebDriver driver,String user)
{
	WebElement toDelete=null;
	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {
				   if(sub_sub_option.getAttribute("info")!=null)
				   {
					   System.out.println(""+sub_sub_option.getAttribute("info"));
				   if(sub_sub_option.getAttribute("info").contains("This email is considered a secure email.  Since you do not have a secure email license, your recipients are required to.  This email is considered a file transfer.  Since you do not have a file transfer license, your recipients are required to."))
				   
				   { System.out.println(""+sub_sub_option.getAttribute("info"));
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);assertEquals("true","false");}
				   }
				   else
				   {
					   toDelete=sub_sub_option;
					   
					   
				  }  
				   
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for	
	   toDelete.click();	   
	   System.out.println("Removed user "+user+" from recipient list");
}// end of LftAndSecureerrorMssg


public static void NoErrorMssg(WebDriver driver,String user)
{

	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {
				   
				   if(sub_sub_option.getAttribute("info")==null)
				   
				   { 
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);assertEquals("true","false");}
				   
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for	
}// end of NoErrorMssg


}//end of Functions class