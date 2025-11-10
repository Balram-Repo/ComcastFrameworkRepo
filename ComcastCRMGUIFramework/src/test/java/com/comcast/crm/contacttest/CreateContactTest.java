package com.comcast.crm.contacttest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.comcast.crm.baseutility.BaseClass;
import com.comcast.crm.objectrepositoryutility.ContactInfoPage;
import com.comcast.crm.objectrepositoryutility.ContactsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewContactPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;
import com.comcast.crm.objectrepositoryutility.SelectOrganizationPage;


@Listeners(com.comcast.crm.generic.listeners.ListenersImpl.class)
public class CreateContactTest extends BaseClass {

	@Test(groups = "smokeTest")
	public void createContactTest() throws Exception {

		// Get data from Excel file
		String lastname = eUtil.getDataFromExcelFile("Contacts", 1, 1) + jUtil.getRandomNumber();

		Reporter.log(  "Navigated to Home Page");
		// 2. Click on Contacts.
		HomePage hp = new HomePage(driver);
		hp.getContactLink().click();
		Reporter.log(  "Navigated to Contact Page");

		// 3.Click on Create Contact Lookup image.
		ContactsPage cp = new ContactsPage(driver);
		cp.getCreateContactBtn().click();
		Reporter.log(  "Creating a New Contact");

		// 4. Enter all the mandatory fields and click on Save button.
		CreatingNewContactPage cncp = new CreatingNewContactPage(driver);
		cncp.getLastnameEdt().sendKeys(lastname);
		cncp.getSaveBtn().click();

		// 5. Verify that the contact is created and the organization name is
		// displayed.
		ContactInfoPage cip = new ContactInfoPage(driver);
		String contactInfo = cip.getHeaderInfo().getText();
		boolean conactStatus = contactInfo.contains(lastname);
		Assert.assertEquals(conactStatus, true);
		Reporter.log(  lastname+" Contact Created");


		String actLastname = cip.getLastnameInfo().getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actLastname.trim(), lastname);
		sa.assertAll();
		Reporter.log(  lastname+" Info Verified");

		// 6. Update the contact Id in the Test Script Data.
		String contactId = cip.getContactIdInfo().getText();
		contactId = contactId.trim();
		Reporter.log(contactId, true);

		// Write back to Excel File
		eUtil.setDataToExcel("Contacts", 1, 4, contactId);
	}

	@Test(groups = "regressionTest")
	public void createContactWithOrgTest() throws Exception {

		// Generate Random number
		int randomNumber = jUtil.getRandomNumber();

		// Get data from Excel file
		String lastname = eUtil.getDataFromExcelFile("Contacts", 7, 1) + randomNumber;
		String orgName = eUtil.getDataFromExcelFile("Contacts", 7, 2) + randomNumber;

		String parentId = driver.getWindowHandle();

		// Object of POM Utilities
		HomePage hp = new HomePage(driver);
		OrganizationsPage op = new OrganizationsPage(driver);
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		ContactsPage cp = new ContactsPage(driver);
		CreatingNewContactPage cncp = new CreatingNewContactPage(driver);
		SelectOrganizationPage sop = new SelectOrganizationPage(driver);
		ContactInfoPage cip = new ContactInfoPage(driver);

		Reporter.log(  "Navigated to Home Page");
		// Create an Organization
		hp.getOrgLink().click();
		Reporter.log(  "Navigated to Organization Page");
		op.getCreateOrgBtn().click();
		cnop.getOrgName().sendKeys(orgName);
		cnop.getSaveBtn().click();

		String orgInfo = oip.getHeaderInfo().getText();
		boolean orgStatus = orgInfo.contains(orgName);
		Assert.assertEquals(orgStatus, true);
		Reporter.log(  orgName+" Organization Created");

		// 2. Click on Contacts.
		hp.getContactLink().click();
		Reporter.log(  "Navigated to Contact Page");

		// 3.Click on Create Contact Lookup image.
		cp.getCreateContactBtn().click();

		// 4. Enter lastname and mobile fields and click on Save button.
		cncp.getLastnameEdt().sendKeys(lastname);
		cncp.getSelectOrgBtn().click();

		// Switch to organization window
		wUtil.toSwitchWindowWithURL(driver, "module=Accounts");

		sop.getSearchEdt().sendKeys(orgName);
		sop.getSearchBtn().click();
		driver.findElement(By.linkText(orgName)).click();

		// Switch to Parent window
		driver.switchTo().window(parentId);

		cncp.getSaveBtn().click();

		// 5. Verify that the contact is created and the organization name is
		// displayed.
		String contactInfo = cip.getHeaderInfo().getText();
		boolean conactStatus = contactInfo.contains(lastname);
		Assert.assertEquals(conactStatus, true);
		Reporter.log(  lastname+" Contact Created");

		String actLastname = cip.getLastnameInfo().getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actLastname.trim(), lastname);
		sa.assertAll();
		Reporter.log(  lastname+" Info Verified");

		String actOrgName = cip.getOrgNameInfo().getText();
		sa.assertEquals(actOrgName.trim(), orgName);
		sa.assertAll();

		// 6. Update the contact Id in the Test Script Data.
		String contactId = cip.getContactIdInfo().getText();
		contactId = contactId.trim();
		Reporter.log(  "Contact ID: "+contactId);

		// Write back to Excel File
		eUtil.setDataToExcel("Contacts", 7, 4, contactId);
	}

	@Test(groups = "regressionTest")
	public void createContactWithMobileTest() throws Exception {

		// Get data from Excel file
		String lastname = eUtil.getDataFromExcelFile("Contacts", 4, 1) + jUtil.getRandomNumber();
		String mobile = eUtil.getDataFromExcelFile("Contacts", 4, 2);

		// Object of POM Utilities
		HomePage hp = new HomePage(driver);
		ContactsPage cp = new ContactsPage(driver);
		CreatingNewContactPage cncp = new CreatingNewContactPage(driver);
		ContactInfoPage cip = new ContactInfoPage(driver);

		Reporter.log(  "Navigated to Home Page");
		// 2. Click on Contacts.
		hp.getContactLink().click();
		Reporter.log(  "Navigated to Contact Page");

		// 3.Click on Create Contact Lookup image.
		cp.getCreateContactBtn().click();

		// 4. Enter lastname and mobile fields and click on Save button.
		cncp.getLastnameEdt().sendKeys(lastname);
		cncp.getMobileEdt().sendKeys(mobile);
		cncp.getSaveBtn().click();

		// 5. Verify that the contact is created and the Mobile no is displayed.
		String contactInfo = cip.getHeaderInfo().getText();
		boolean conactStatus = contactInfo.contains(lastname);
		Assert.assertEquals(conactStatus, true);
		Reporter.log(  lastname+" Contact Created");

		String actLastname = cip.getLastnameInfo().getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actLastname.trim(), lastname);
		sa.assertAll();
		Reporter.log(  lastname+" Info Verified");

		String actMobile = cip.getMobileInfo().getText();
		sa.assertEquals(actMobile.trim(), mobile);
		sa.assertAll();
		Reporter.log(  mobile+ "Info Verified");

		// 6. Update the contact Id in the Test Script Data.
		String contactId = cip.getContactIdInfo().getText();
		contactId = contactId.trim();

		// Write back to Excel File
		eUtil.setDataToExcel("Contacts", 4, 4, contactId);

	}
}
