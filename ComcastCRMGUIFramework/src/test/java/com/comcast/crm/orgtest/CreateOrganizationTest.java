package com.comcast.crm.orgtest;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.comcast.crm.baseutility.BaseClass;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.objectrepositoryutility.CreatingNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationsPage;

@Listeners(com.comcast.crm.generic.listeners.ListenersImpl.class)
public class CreateOrganizationTest extends BaseClass {

	@Test(groups = "smokeTest")
	public void createOrganizationTest() throws Exception {

		// Get data from Excel file
		String orgName = eUtil.getDataFromExcelFile("Org", 1, 1) + jUtil.getRandomNumber();

		// Object of POM Utilities
		HomePage hp = new HomePage(driver);
		OrganizationsPage op = new OrganizationsPage(driver);
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);

		UtilityClassObject.getTest().log(Status.INFO, "Navigated to Home Page");
		// 2. Click on Organization.
		hp.getOrgLink().click();
		UtilityClassObject.getTest().log(Status.INFO,  "Navigated to Organization Page");

		// 3.Click on Create Organization Lookup image.
		op.getCreateOrgBtn().click();

		// 4. Enter all the mandatory fields and click on Save button.
		cnop.getOrgName().sendKeys(orgName);
		cnop.getSaveBtn().click();

		// 5. Verify that the organization is created and the organization name is
		// displayed.
		String orgInfo = oip.getHeaderInfo().getText();
		boolean orgStatus = orgInfo.contains(orgName);
		Assert.assertEquals(orgStatus, true);
		UtilityClassObject.getTest().log(Status.INFO, orgName+" Organization Created");

		String actOrgName = oip.getOrgNameInfo().getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actOrgName.trim(), orgName);
		sa.assertAll();
		UtilityClassObject.getTest().log(Status.INFO,  orgName+" Info Verified");

		// 6. Update the organization Id in the Test Script Data.
		String orgId = oip.getOrgIdInfo().getText();
		orgId = orgId.trim();
		UtilityClassObject.getTest().log(Status.INFO,  "OrgID: "+orgId);

		// Write back to excel file
		eUtil.setDataToExcel("Org", 1, 4, orgId);
	}

	@Test(groups = "regressionTest")
	public void createOrgWithIndustryTest() throws Exception {
		// Get data from Excel file
		String orgName = eUtil.getDataFromExcelFile("Org", 4, 1) + jUtil.getRandomNumber();
		String industryName = eUtil.getDataFromExcelFile("Org", 4, 2);

		// Object of POM Utilities
		HomePage hp = new HomePage(driver);
		OrganizationsPage op = new OrganizationsPage(driver);
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);

		UtilityClassObject.getTest().log(Status.INFO,  "Navigated to Home Page");
		// 2. Click on Organization.
		hp.getOrgLink().click();
		UtilityClassObject.getTest().log(Status.INFO,  "Navigated to Organization Page");

		// 3.Click on Create Organization Lookup image.
		op.getCreateOrgBtn().click();

		// 4. Enter company name and industry fields and click on Save button.
		cnop.getOrgName().sendKeys(orgName);
		wUtil.toSelect(cnop.getIndustryDD(), industryName);
		cnop.getSaveBtn().click();

		// 5. Verify that the organization is created and the organization name is
		// displayed.
		String orgInfo = oip.getHeaderInfo().getText();
		boolean orgStatus = orgInfo.contains(orgName);
		Assert.assertEquals(orgStatus, true);
		UtilityClassObject.getTest().log(Status.INFO,  orgName+" Organization Created");

		
		String actOrgName = oip.getOrgNameInfo().getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actOrgName.trim(), orgName);
		sa.assertAll();
		UtilityClassObject.getTest().log(Status.INFO,  orgName+" Info Verified");

		String actIndustryName = oip.getIndustryNameInfo().getText();
		sa.assertEquals(actIndustryName, industryName);
		sa.assertAll();
		UtilityClassObject.getTest().log(Status.INFO, industryName+" Info Verified");

		// 6. Update the organization Id in the Test Script Data.
		String orgId = oip.getOrgIdInfo().getText();
		orgId = orgId.trim();
		UtilityClassObject.getTest().log(Status.INFO,  "OrgID: "+orgId);

		// Write back to excel file
		eUtil.setDataToExcel("Org", 4, 4, orgId);

	}

	@Test(groups = "regressionTest")
	public void createOrgWithTypeTest() throws Exception {
		// Get data from Excel file
		String orgName = eUtil.getDataFromExcelFile("Org", 7, 1) + jUtil.getRandomNumber();
		String typeName = eUtil.getDataFromExcelFile("Org", 7, 2);

		// Object of POM Utilities
		HomePage hp = new HomePage(driver);
		OrganizationsPage op = new OrganizationsPage(driver);
		CreatingNewOrganizationPage cnop = new CreatingNewOrganizationPage(driver);
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);

		UtilityClassObject.getTest().log(Status.INFO, "Navigated to Home Page");
		// 2. Click on Organization.
		hp.getOrgLink().click();
		UtilityClassObject.getTest().log(Status.INFO, "Navigated to Organization Page");

		// 3.Click on Create Organization Lookup image.
		op.getCreateOrgBtn().click();

		// 4. Enter company name and type fields and click on Save button.
		cnop.getOrgName().sendKeys(orgName);
		wUtil.toSelect(cnop.getTypeDD(), typeName);
		cnop.getSaveBtn().click();

		// 5. Verify that the organization is created and the organization name is
		// displayed.
		String orgInfo = oip.getHeaderInfo().getText();
		boolean orgStatus = orgInfo.contains(orgName);
		Assert.assertEquals(orgStatus, true);
		UtilityClassObject.getTest().log(Status.INFO, orgName+" Organization Created");

		String actOrgName = oip.getOrgNameInfo().getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actOrgName.trim(), orgName);
		sa.assertAll();
		UtilityClassObject.getTest().log(Status.INFO,  orgName+" Info Verified");

		String actTypeName = oip.getTypeInfo().getText();
		sa.assertEquals(actTypeName, typeName);
		sa.assertAll();
		UtilityClassObject.getTest().log(Status.INFO,  typeName+" Info VErified");

		// 6. Update the organization Id in the Test Script Data.
		String orgId = oip.getOrgIdInfo().getText();
		orgId = orgId.trim();
		UtilityClassObject.getTest().log(Status.INFO,  "OrgID: "+orgId);

		// Write back to excel file
		eUtil.setDataToExcel("Org", 7, 4, orgId);

	}
}
