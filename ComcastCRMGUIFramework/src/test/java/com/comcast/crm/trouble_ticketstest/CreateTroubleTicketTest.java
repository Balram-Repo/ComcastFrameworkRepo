package com.comcast.crm.trouble_ticketstest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.comcast.crm.baseutility.BaseClass;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.objectrepositoryutility.ContactInfoPage;
import com.comcast.crm.objectrepositoryutility.ContactsPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewContactPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewProductPage;
import com.comcast.crm.objectrepositoryutility.CreatingNewTicketPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.ProductInfoPage;
import com.comcast.crm.objectrepositoryutility.ProductsPage;
import com.comcast.crm.objectrepositoryutility.SelectContactPage;
import com.comcast.crm.objectrepositoryutility.SelectProductPage;
import com.comcast.crm.objectrepositoryutility.TroubleTicketsInfoPage;
import com.comcast.crm.objectrepositoryutility.TroubleTicketsPage;

@Listeners(com.comcast.crm.generic.listeners.ListenersImpl.class)
public class CreateTroubleTicketTest extends BaseClass {

	@Test(groups = "regressionTest")
	public void createTroubleTicketTest() throws Exception {

		// Get data from Excel file
		String ticket_title = eUtil.getDataFromExcelFile("Trouble_Ticket", 1, 1);
		String lastname = eUtil.getDataFromExcelFile("Trouble_Ticket", 1, 2) + jUtil.getRandomNumber();
		String prodName = eUtil.getDataFromExcelFile("Trouble_Ticket", 1, 4);
		String statusWFR = eUtil.getDataFromExcelFile("Trouble_Ticket", 1, 7);

		// Object of POM Utilities
		HomePage hp = new HomePage(driver);
		TroubleTicketsPage ttp = new TroubleTicketsPage(driver);
		CreatingNewTicketPage cntp = new CreatingNewTicketPage(driver);
		ContactsPage cp = new ContactsPage(driver);
		CreatingNewContactPage cncp = new CreatingNewContactPage(driver);
		ContactInfoPage cip = new ContactInfoPage(driver);
		SelectContactPage scp = new SelectContactPage(driver);
		ProductsPage pp = new ProductsPage(driver);
		CreatingNewProductPage cnpp = new CreatingNewProductPage(driver);
		ProductInfoPage pip = new ProductInfoPage(driver);
		SelectProductPage spp = new SelectProductPage(driver);
		TroubleTicketsInfoPage ttip = new TroubleTicketsInfoPage(driver);

		// Pre-Conditions
		// Create Contact
		hp.getContactLink().click();
		cp.getCreateContactBtn().click();
		cncp.getLastnameEdt().sendKeys(lastname);
		cncp.getSaveBtn().click();
		String contactInfo = cip.getHeaderInfo().getText();
		boolean contactStatus = contactInfo.contains(lastname);
		Assert.assertTrue(contactStatus);
		UtilityClassObject.getTest().log(Status.INFO, "Contact Created");

		// Create Product
		hp.getProductLink().click();
		pp.getCreateProductBtn().click();
		cnpp.getProductNameEdt().sendKeys(prodName);
		cnpp.getSaveBtn().click();
		String prodInfo = pip.getHeaderInfo().getText();
		boolean productStatus = prodInfo.contains(prodName);
		Assert.assertTrue(productStatus);
		UtilityClassObject.getTest().log(Status.INFO, "Product Created");

		// Create Trouble Ticket
		hp.getTrouble_TicketLink().click();
		ttp.getCreateTicketBtn().click();
		cntp.getTitleEdt().sendKeys(ticket_title);
		UtilityClassObject.getTest().log(Status.INFO, "Title Selected");

		cntp.getSelectContactImg().click();
		// Switch driver control to Contacts Page
		wUtil.toSwitchWindowWithURL(driver, "module=Contacts");
		scp.getSearchEdt().sendKeys(lastname);
		scp.getSearchBtn().click();
		driver.findElement(By.linkText(lastname)).click();

		// Switch back driver control
		wUtil.toSwitchWindowWithURL(driver, "module=HelpDesk");

		cntp.getSelectProductImg().click();
		// Switch driver control to Products Page
		wUtil.toSwitchWindowWithURL(driver, "module=Contacts");
		spp.getSearchEdt().sendKeys(prodName);
		spp.getSearchBtn().click();
		driver.findElement(By.linkText(prodName)).click();
		UtilityClassObject.getTest().log(Status.INFO, "Product Selected");

		// Switch back driver control
		wUtil.toSwitchWindowWithURL(driver, "module=HelpDesk");

		wUtil.toSelect(cntp.getStatusDD(), statusWFR);
		UtilityClassObject.getTest().log(Status.INFO, "Status Selected");

		cntp.getSaveBtn().click();

		// Verification
		String ticketInfo = ttip.getHeaderInfo().getText();
		boolean ticketStatus = ticketInfo.contains(ticket_title);
		Assert.assertTrue(ticketStatus);
		UtilityClassObject.getTest().log(Status.INFO, "Trouble Ticket Created");

		String actTicketTitle = ttip.getTitleInfo().getText();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(actTicketTitle.trim(), ticket_title);
		sa.assertAll();

		String contactTitle = ttip.getContactNameInfo().getText();
		sa.assertEquals(contactTitle.trim(), lastname);
		sa.assertAll();

		String prodTitle = ttip.getProductNameInfo().getText();
		sa.assertEquals(prodTitle.trim(), prodName);
		sa.assertAll();

		String statusTitle = ttip.getStatusInfo().getText();
		sa.assertEquals(statusTitle, statusWFR);
		sa.assertAll();

		String ticketId = ttip.getTicketIdInfo().getText();
		ticketId = ticketId.trim();

		// Write back to excel file
		eUtil.setDataToExcel("Trouble_Ticket", 1, 6, ticketId);

	}
}
