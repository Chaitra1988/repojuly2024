package common;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import driver.DriverScript;
import pages.HomePage;
import pages.LoginPage;
import pages.UserPage;

public class AppDependentMethods extends DriverScript{
	/*************************************
	 * method Name	: navigateURL()
	 * purpose		: it will navigate the URL
	 * 
	 *********************************/
	public boolean navigateURL(WebDriver oBrowser, String strURL, String pageTitle) {
		try {
			oBrowser.navigate().to(strURL);
			Thread.sleep(2000);
			report.writeReport(oBrowser, "Screenshot", "The URL '"+strURL+"' is navigated successful");
			return appInd.compareExactValue(oBrowser, oBrowser.getTitle(), pageTitle);
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'navigateURL() method'. " + e);
			return false;
		}
	}
	
	
	
	/*************************************
	 * method Name	: loginToActiTime()
	 * purpose		: it will login to actiTime application
	 * 
	 *********************************/
	public boolean loginToActiTime(WebDriver oBrowser, String userName, String password) {
		try {
			Assert.assertTrue(appInd.setObject(oBrowser, LoginPage.obj_UserName_Edit, userName));
			Assert.assertTrue(appInd.setObject(oBrowser, LoginPage.obj_Password_Edit, password));
			Assert.assertTrue(appInd.clickObject(oBrowser, LoginPage.obj_Login_Button));
			appInd.waitForElement(oBrowser, HomePage.obj_SaveChanges_Button, "Clickable", "", 10);
			report.writeReport(oBrowser, "Screenshot", "Login to actiTime successful");
			boolean blnRes = appInd.verifyText(oBrowser, HomePage.obj_HomePage_Title, "Text", "Enter Time-Track");
			if(blnRes) {
				//Handle the shortcut window
				if(appInd.verifyOptionalElementPresent(oBrowser, HomePage.obj_ShortCut_Window)){
					Assert.assertTrue(appInd.clickObject(oBrowser, HomePage.obj_ShortBy_Close_Button));
				}
				return true;
			}else return false;
			
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'loginToActiTime() method'. " + e);
			return false;
		}catch(AssertionError e) {
			report.writeReport(oBrowser, "Exception", "Assert Error in 'loginToActiTime() method'. " + e);
			return false;
		}
	}
	
	
	
	/**********************************************
	 * Method Name		: logoutFromActiTime()
	 * Purpose			: it is used to logout from the actiTime
	 * Author			: tester1
	 * Date created		:
	 * Modified By		:
	 * Date modified	:
	 **********************************************/
	public boolean logoutFromActiTime(WebDriver oBrowser) {
		try {
			report.writeReport(oBrowser, "Screenshot", "Before logout from actiTime");
			Assert.assertTrue(appInd.clickObject(oBrowser, HomePage.obj_Logout_Link));
			appInd.waitForElement(oBrowser, LoginPage.obj_LoginPage_Header, "Text", "Please identify yourself", 10);
			report.writeReport(oBrowser, "Screenshot", "After logout from actiTime");
			return appInd.verifyText(oBrowser, LoginPage.obj_LoginPage_Header, "Text", "Please identify yourself");
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'logoutFromActiTime() method'. " + e);
			return false;
		}catch(AssertionError e) {
			report.writeReport(oBrowser, "Exception", "Assert Error in 'logoutFromActiTime() method'. " + e);
			return false;
		}
	}
	
	
	
	/**********************************************
	 * Method Name		: createUser()
	 * Purpose			: it is used to create the new user
	 * Author			: tester1
	 * Date created		:
	 * Modified By		:
	 * Date modified	:
	 **********************************************/
	public String createUser(WebDriver oBrowser, Map<String, String> data) {
		String userName = null;
		try {
			Assert.assertTrue(appInd.clickObject(oBrowser, UserPage.obj_USERS_Menu));
			appInd.waitForElement(oBrowser, UserPage.obj_AddUser_Button, "Clickable", "", 10);
			Assert.assertTrue(appInd.clickObject(oBrowser, UserPage.obj_AddUser_Button));
			appInd.waitForElement(oBrowser, UserPage.obj_CreateUser_Button, "Clickable", "", 10);
			report.writeReport(oBrowser, "Screenshot", "Before entering the user details for creating new user");
			Assert.assertTrue(appInd.setObject(oBrowser, UserPage.obj_User_FirstName_Edit, data.get("user_firstName")));
			Assert.assertTrue(appInd.setObject(oBrowser, UserPage.obj_User_LastName_Edit, data.get("user_lastName")));
			Assert.assertTrue(appInd.setObject(oBrowser, UserPage.obj_User_Email_Edit, data.get("user_email")));
			Assert.assertTrue(appInd.setObject(oBrowser, UserPage.obj_User_UserName_Edit, data.get("user_userName")));
			Assert.assertTrue(appInd.setObject(oBrowser, UserPage.obj_User_Password_Edit, data.get("user_password")));
			Assert.assertTrue(appInd.setObject(oBrowser, UserPage.obj_User_RetypePassword_Edit, data.get("user_retypePassword")));
			Assert.assertTrue(appInd.clickObject(oBrowser, UserPage.obj_CreateUser_Button));
			userName = data.get("user_lastName")+", "+data.get("user_firstName");
			appInd.waitForElement(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"), "Text", userName, 10);
			
			report.writeReport(oBrowser, "Screenshot", "The new user '"+userName+"' has created successful");
			if(appInd.verifyElementPresent(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"))){
				return userName;
			}else return null;
		}catch(Exception e) {
			report.writeReport(oBrowser, "Exception", "Exception in 'createUser() method'. " + e);
			return null;
		}catch(AssertionError e) {
			report.writeReport(oBrowser, "Exception", "Assert Error in 'createUser() method'. " + e);
			return null;
		}finally {userName = null;}
	}
	
	
	
	/**********************************************
	 * Method Name		: deleteUser()
	 * Purpose			: it is to delete the user
	 * Author			: tester1
	 * Date created		:
	 * Modified By		:
	 * Date modified	:
	 **********************************************/
	public boolean deleteUser(WebDriver oBrowser, String userName) {
		try {
			report.writeReport(oBrowser, "Screenshot", "Before deleting the user");
			Assert.assertTrue(appInd.clickObject(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']")));
			appInd.waitForElement(oBrowser, UserPage.obj_DeleteUser_Button, "Clickable", "", 10);
			Assert.assertTrue(appInd.clickObject(oBrowser, UserPage.obj_DeleteUser_Button));
			appInd.waitForElement(oBrowser, null, "Alert", "", 10);
			oBrowser.switchTo().alert().accept();
			appInd.waitForElement(oBrowser, UserPage.obj_DeleteUser_Button, "invisible", "", 10);
			report.writeReport(oBrowser, "Screenshot", "After deleting the user");
			return appInd.verifyElementNotPresent(oBrowser, By.xpath("//div[@class='name']/span[text()='"+userName+"']"));
		}catch(Exception e) {
			System.out.println("Exception in 'deleteUser() method'. " + e);
			return false;
		}catch(AssertionError e) {
			report.writeReport(oBrowser, "Exception", "Assert Error in 'deleteUser() method'. " + e);
			return false;
		}
	}
}
