package com.comcast.crm.calendartest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.comcast.crm.baseutility.BaseClass;
import com.comcast.crm.generic.webdriverutility.UtilityClassObject;
import com.comcast.crm.objectrepositoryutility.AddEventPage;
import com.comcast.crm.objectrepositoryutility.AllEventsAndTodosPage;
import com.comcast.crm.objectrepositoryutility.CalendarPage;
import com.comcast.crm.objectrepositoryutility.CreateToDoPage;
import com.comcast.crm.objectrepositoryutility.EventInfoPage;
import com.comcast.crm.objectrepositoryutility.HomePage;

@Listeners(com.comcast.crm.generic.listeners.ListenersImpl.class)
public class CreateAnEventTest extends BaseClass {

	@Test(groups = "smokeTest")
	public void createACallEvent() throws Exception {

		// Get Data from Excel File
		String eventName = eUtil.getDataFromExcelFile("Calendar", 1, 1);
		String startDate = jUtil.getSystemDateYYYYMMDD();
		String endDate = eUtil.getDataFromExcelFile("Calendar", 1, 3);
		String startHour = eUtil.getDataFromExcelFile("Calendar", 1, 4);
		String startMinute = eUtil.getDataFromExcelFile("Calendar", 1, 5);
		String endHour = eUtil.getDataFromExcelFile("Calendar", 1, 6);
		String endMinute = eUtil.getDataFromExcelFile("Calendar", 1, 7);

		// Object of POM Classes
		HomePage hp = new HomePage(driver);
		CalendarPage cp = new CalendarPage(driver);
		AddEventPage aep = new AddEventPage(driver);
		AllEventsAndTodosPage aeatp = new AllEventsAndTodosPage(driver);
		EventInfoPage eip = new EventInfoPage(driver);

		hp.getCalendarLink().click();
		UtilityClassObject.getTest().log(Status.INFO, "Navigated to Calender Page");

		wUtil.toMouseHover(driver, cp.getAddBtn());
		cp.getAddCallLink().click();
		UtilityClassObject.getTest().log(Status.INFO,"Add and Event Page Opened");

		// Fill Details
		aep.getEventNameEdt().sendKeys(eventName);

		aep.getStartDateEdt().clear();
		aep.getStartDateEdt().sendKeys(startDate);
		wUtil.toSelect(aep.getStartHrEdt(), startHour);
		wUtil.toSelect(aep.getStartMinEdt(), startMinute);

		aep.getEndDateEdt().clear();
		aep.getEndDateEdt().sendKeys(endDate);
		wUtil.toSelect(aep.getEndHrEdt(), endHour);
		wUtil.toSelect(aep.getEndMinEdt(), endMinute);

		aep.getSaveBtn().click();
		UtilityClassObject.getTest().log(Status.INFO, "Filled the details and Saved");

		// Verification
		cp.getAllEventsAndToDoLink().click();
		aeatp.getSearchEdt().sendKeys(eventName);
		aeatp.getSearchBtn().click();
		wUtil.visibilityOfElement(driver, driver.findElement(By.linkText(eventName)));

		driver.findElement(By.linkText(eventName)).click();

		String eventInfo = eip.getHeaderInfo().getText();
		boolean eventStatus = eventInfo.contains(eventName);
		Assert.assertTrue(eventStatus);
		UtilityClassObject.getTest().log(Status.INFO, eventName+" Event Created===> Success");
		UtilityClassObject.getTest().log(Status.INFO, "Call Event Created");
		
		// Get Event ID
		String eventId = "";
		int start = eventInfo.indexOf("[");
		int end = eventInfo.indexOf("]");
		for (int i = start + 1; i < end; i++) {
			char ch = eventInfo.charAt(i);
			eventId += ch;
		}
		eventId = eventId.trim();

		// Write back to excel file
		eUtil.setDataToExcel("Calendar", 1, 9, eventId);
	}

	@Test(groups = "smokeTest")
	public void createAToDo() throws Exception {
		String todoSubject = eUtil.getDataFromExcelFile("Calendar", 7, 1);
		String startDate = jUtil.getSystemDateYYYYMMDD();
		String endDate = eUtil.getDataFromExcelFile("Calendar", 7, 3);

		// Object of POM Classes
		HomePage hp = new HomePage(driver);
		CalendarPage cp = new CalendarPage(driver);
		CreateToDoPage ctdp = new CreateToDoPage(driver);
		AllEventsAndTodosPage aeatp = new AllEventsAndTodosPage(driver);
		EventInfoPage eip = new EventInfoPage(driver);

		hp.getCalendarLink().click();
		UtilityClassObject.getTest().log(Status.INFO, "Navigated to Calender Page");

		wUtil.toMouseHover(driver, cp.getAddBtn());
		cp.getAddToDoLink().click();
		UtilityClassObject.getTest().log(Status.INFO, "Create ToDo Page Opened");

		// Fill Details
		ctdp.getTodoNameEdt().sendKeys(todoSubject);

		ctdp.getStartDateEdt().clear();
		ctdp.getStartDateEdt().sendKeys(startDate);

		for (int i = 0; i <= 10; i++) {
			ctdp.getEndDateEdt().sendKeys(Keys.BACK_SPACE);
		}
		ctdp.getEndDateEdt().sendKeys(endDate);

		ctdp.getSaveBtn().click();
		UtilityClassObject.getTest().log(Status.INFO, "Filled Details and Saved");

		// Verification
		cp.getAllEventsAndToDoLink().click();
		aeatp.getSearchEdt().sendKeys(todoSubject);
		aeatp.getSearchBtn().click();
		wUtil.visibilityOfElement(driver, driver.findElement(By.linkText(todoSubject)));

		driver.findElement(By.linkText(todoSubject)).click();

		String todoInfo = eip.getHeaderInfo().getText();
		boolean todoStatus = todoInfo.contains(todoSubject);
		Assert.assertTrue(todoStatus);
		UtilityClassObject.getTest().log(Status.INFO, todoSubject+" ToDo Created ===> Success");
		UtilityClassObject.getTest().log(Status.INFO, "ToDo Created");
		
		// Get ToDo Id
		String todoId = "";
		int start = todoInfo.indexOf("[");
		int end = todoInfo.indexOf("]");
		for (int i = start + 1; i < end; i++) {
			char ch = todoInfo.charAt(i);
			todoId += ch;
		}
		todoId = todoId.trim();
		UtilityClassObject.getTest().log(Status.INFO, todoId);

		// Write back to excel file
		eUtil.setDataToExcel("Calendar", 7, 7, todoId);
	}
}
