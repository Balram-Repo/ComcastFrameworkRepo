package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Documents")
	private WebElement documentsLink;
	
	@FindBy(linkText = "Organizations")
	private WebElement orgLink;

	@FindBy(linkText = "Contacts")
	private WebElement contactLink;

	@FindBy(linkText = "Products")
	private WebElement productLink;

	@FindBy(linkText = "Calendar")
	private WebElement calendarLink;

	@FindBy(linkText = "Trouble Tickets")
	private WebElement trouble_TicketLink;

	@FindBy(linkText = "More")
	private WebElement moreLink;

	@FindBy(name = "Vendors")
	private WebElement vendorsLink;

	@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
	private WebElement userImg;

	@FindBy(linkText = "Sign Out")
	private WebElement signoutLink;

	public WebElement getOrgLink() {
		return orgLink;
	}

	public WebElement getDocumentsLink() {
		return documentsLink;
	}

	public WebElement getContactLink() {
		return contactLink;
	}

	public WebElement getProductLink() {
		return productLink;
	}

	public WebElement getCalendarLink() {
		return calendarLink;
	}

	public WebElement getTrouble_TicketLink() {
		return trouble_TicketLink;
	}

	public WebElement getMoreLink() {
		return moreLink;
	}

	public WebElement getVendorsLink() {
		return vendorsLink;
	}

	public WebElement getUserImg() {
		return userImg;
	}

	public WebElement getSignoutLink() {
		return signoutLink;
	}

	public void navigateToVendors(WebDriver driver) {
		Actions act = new Actions(driver);
		act.moveToElement(moreLink).perform();
		vendorsLink.click();
	}
	
	public void signout(WebDriver driver) {
		Actions act = new Actions(driver);
		act.moveToElement(userImg).perform();
		signoutLink.click();
	}
}
