package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.ElementUtil;
import utilities.ExcelReader;
import utilities.Log;
import utilities.ReadConfig;
import utilities.RunTimeData;

public class ProgramPage extends CommonPage {

	private WebDriver driver;
	private ElementUtil util;
	ReadConfig readConfig;
	Actions actions;

	private String sheetName = "Program";

	Map<String, String> programData;
	private static final Logger log = LogManager.getLogger(ProgramPage.class);

	public ProgramPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		util = new ElementUtil(this.driver);
		readConfig = new ReadConfig();
		actions = new Actions(driver);
	}

	By programPageLMSHeading = By.xpath("//*[contains(text(),'LMS - Learning Management System')]");
	By manageProgramTitle = By.xpath("//*[contains(text(),'Manage Program')]");
	By addNewProgramPopUp = By.xpath("//app-program/p-dialog/div/div");
	By addNewProgramTitle = By.xpath("//*[contains(text(),'Program Details')]");
	By btn_AddNewProgram = By.xpath("//button[text()='Add New Program']");
	By requiredFieldErrorMsgs = By.xpath("//small[@class='p-invalid ng-star-inserted']");
	By searchBar = By.xpath("//input[@id='filterGlobal']");

	By headerBar = By.xpath("//mat-toolbar[@class='mat-toolbar mat-primary mat-toolbar-single-row ng-star-inserted']");
	By programResultsTable = By.xpath("//table/tbody//tr");
	By programNameInput = By.id("programName");
	By programDescInput = By.id("programDescription");
	By saveButton = By.id("saveProgram");
	By cancelButton = By.xpath("//button[@label='Cancel']");
	By xButton = By.xpath("//*[@header='Program Details']//button[@type='button']");
	By xDeleteButton = By.xpath("//*[contains(@class,' p-dialog-header-close p-link ng-star-inserted')]/..");

	By deleteButton = By.xpath("//div[@class='action']//button[@icon='pi pi-trash']");
	By deleteYesBtn = By.xpath("//span[normalize-space()='Yes']");
	By deleteNoBtn = By.xpath("//button/span[@class='p-button-label' and text()='No']");

	By successPopupTitle = By.xpath("//div[contains(@class,'p-toast-summary')]");
	By successPopupContent = By.xpath("//div[contains(@class,'p-toast-detail')]");

	By addNewProgramSuccessMsg = By.xpath("//div[@class='p-toast-message-content ng-tns-c20-13']");

	// Table
	By programNameHeading = By.xpath("//table/thead/tr/th[2]");
	By programDescHeading = By.xpath("//table/thead/tr/th[3]");
	By programStatusHeading = By.xpath("//table/thead/tr/th[4]");
	By deleteBtnManageProgram = By.xpath("//button[@class='p-button-danger p-button p-component p-button-icon-only']");
	By programTable = By.xpath("//table/tbody");
	By footerPrograms = By.xpath("//div[@class='p-d-flex p-ai-center p-jc-between ng-star-inserted']");

	// Tohfa
	By checkBoxHeader = By.xpath(".//table/thead/tr/th[1]/p-tableheadercheckbox/div/div[2]/span");
	// By searchBox = By.xpath("//input[@placeholder='Search...']");
	@FindBy(id = "filterGlobal")
	WebElement searchBox;

	// Pagination

	By prevPaginatorBtn = By.xpath("//button[contains(@class,'p-paginator-prev')]");
	By firstPaginatorBtn = By.xpath("//button[contains(@class,'p-paginator-first')]");
	By nextPaginatorBtn = By.xpath("//button[contains(@class,'p-paginator-next')]");
	By lastPaginatorBtn = By.xpath("//button[contains(@class,'p-paginator-last')]");

	// sort element locators

	By programNameSort = By.xpath("//thead//tr//th[2]//i");
	By programDescriptionSort = By.xpath("//thead//tr//th[3]//i");
	By programStatusSort = By.xpath("//thead//tr//th[4]//i");

	// SortList
	By programNameList = By.xpath("//tbody//td[2]");
	By programDescriptionList = By.xpath("//tbody//td[3]");
	By programStatusList = By.xpath("//tbody//td[4]");

	public String getProgramPageTitle() {
		// return util.getElementText(programPageTitle);
		return util.getElementText(manageProgramTitle);
	}

	public void isLogoutDisplayedMenuBar() {
		util.isElementDisplayed(menu_logout);
		// menu_logout.isDisplayed();
	}

	public String getLMSHeaderMenuBar() {

		return util.getElementText(programPageLMSHeading);

	}

	public String getAddNewProgramSubMenu() {

		return util.getElementText(btn_AddNewProgram);

	}

	public void menuBarDisplay() {

		WebElement headerBarEle = util.getElement(headerBar);
		// List<WebElement> buttons =
		// headerBar.findElements(By.xpath("//div[@class='ng-star-inserted']//button"));
		List<WebElement> buttons = headerBarEle.findElements(By.xpath("//div[@class='ng-star-inserted']//button"));

		Assert.assertTrue(buttons.size() > 0, "Menu headers are not present in the navigation bar");
		for (WebElement button : buttons) {
			String buttonText = button.getText().trim();
			Assert.assertFalse(buttonText.isEmpty(), "Navigation button text is missing for one of the buttons.");
			log.info("Button text: " + buttonText);
		}
	}

	public void clickAddNewProgramBtn() {
		WebElement addNewProgramButton = driver.findElement(btn_AddNewProgram);
		try {
			addNewProgramButton.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewProgramButton);

		}
	}

	public String getAddNewProgramPopUpTitle() {
		return util.getElementText(addNewProgramTitle);
	}

	public boolean isAddNewProgramPopUpDisplayed() {
		return util.isElementDisplayed(addNewProgramPopUp);
	}


	public void fillProgramForm(String testCase) throws Exception {

		programData = ExcelReader.getTestData(sheetName, testCase);

		log.info("Program data from excel --" + programData);

		String programName = programData.get("ProgramName");
		String programDesc = programData.get("ProgramDescription");
		String status = programData.get("ProgramStatus");

		if (testCase.equalsIgnoreCase("validInputData")) {
			programName = programName + util.generateRandomString(3);
		}


		log.info("Program Name Input :" + programName);

		if (programName != null && !programName.isEmpty()) {
			util.doSendKeys(programNameInput, programName);
		} else {
			log.info("Program Name is missing or empty");
		}

		if (programDesc != null && !programDesc.isEmpty()) {
			util.doSendKeys(programDescInput, programDesc);

		} else {
			log.info("Program Description is missing or empty");
		}

		By statusRadioBtn = By.xpath("//input[@id='" + status + "']");
		util.clickElementByJS(statusRadioBtn, driver);

		util.doClick(saveButton);

		RunTimeData.setData("programName", programName);
		RunTimeData.setData("programDesc", programDesc);
		RunTimeData.setData("programstatus", status);

	}

	public void clickSaveBtn() {
		util.doClick(saveButton);
	}

	public void editTheProgramAndClickSave(String testCase) throws InterruptedException {

		String existingProgram = (String) RunTimeData.getData("programName");

		Log.logInfo("ProgramName at run time received in line 235 in ProgramPage = " + existingProgram);

		while (existingProgram == null) {
			Thread.sleep(1000);
			// Then fetch data again
			existingProgram = (String) RunTimeData.getData("programName");
		}

		// Search for Program and Click Edit
		searchUpdatedProgram(existingProgram);
		clickEditProgramBtn(existingProgram);

		getAddNewProgramPopUpTitle().equals("Program Details");

		programData = ExcelReader.getTestData(sheetName, testCase);

		String programNameEdit = programData.get("ProgramName");
		String programDescEdit = programData.get("ProgramDescription");
		String programStatusEdit = programData.get("ProgramStatus");

		log.info("Program name to update from excel >>" + programNameEdit);

		programNameEdit = programNameEdit + util.generateRandomString(3);
		util.mouseclickUsingAction(programNameInput);
		util.clearField(programNameInput);
		util.doSendKeys(programNameInput, programNameEdit);

		util.mouseclickUsingAction(programDescInput);
		util.clearField(programDescInput);
		util.doSendKeys(programDescInput, programDescEdit);

		By statusRadioBtn = By.xpath("//input[@id='" + programStatusEdit + "']");
		util.clickElementByJS(statusRadioBtn, driver);

		util.doClick(saveButton);

		if (getToast().equalsIgnoreCase("Successful") && testCase.equalsIgnoreCase("validInputEditData")) {
			System.out.println("Program updated successfully");
			System.out.println("Updated Program Name: " + programNameEdit);

			RunTimeData.setData("programNameEdit", programNameEdit);
			RunTimeData.setData("programDescEdit", programDescEdit);
			RunTimeData.setData("programStatusEdit", programStatusEdit);

		} else {
			log.info("Program update failed");
		}

	}

	public void deleteTheProgramAndClickSave(String newProgram, String testCase) throws InterruptedException {

		newProgram = (String) RunTimeData.getData("programNameEdit");
		search(newProgram);
		clickDeleteProgramBtn(newProgram);
		util.isElementDisplayed(deleteConfirmationPopUp);

		util.doClick(deleteYesBtn);

		if (getToast().equalsIgnoreCase("Successful")) {
			log.info("Program deleted successfully");
			log.info("Deleted Program Name: " + newProgram);

		} else {
			log.info("Program deletion failed");
		}

	}

	public WebElement getProgramRowElement(String programName) {
		WebElement ele = util.getElement(programTable);
		return ele.findElement(By.xpath("//tr/td[contains(text(),'" + programName + "')]/.."));

	}

	public void clickEditProgramBtn(String programName) {
		WebElement editButton = getProgramRowElement(programName).findElement(By.id("editProgram"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);

	}

	public void clickDeleteProgramBtn(String programName) {

		programName = (String) RunTimeData.getData("programNameEdit");
		log.info("Program to be deleted >>>" + programName);
		WebElement deleteProgBtn = getProgramRowElement(programName).findElement(deleteButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteProgBtn);

	}

	public void clickCancelProgramBtn() {
		util.doClick(cancelButton);
	}

	public void clickYesDeleteBtn() throws InterruptedException {

		Thread.sleep(500);
		util.clickElementByJS(deleteYesBtn, driver);

		log.info("Clicked on Yes...");

	}

	public void clickNoDeleteBtn() {

		util.clickElementByJS(deleteNoBtn, driver);

	}

	public boolean verifySuccessMessage(String message) {

		util.isElementDisplayed(successPopupTitle);

		util.isElementDisplayed(successPopupContent);

		String content = util.getElementText(successPopupContent);
		log.info("Message >>>>" + content);

		if (content.equals(message)) {
			return true;
		}
		return false;
	}

	public String getManageProgramText() {
		return util.getElementText(manageProgramTitle);
	}

	public boolean verifyColumnHeader() {

		if (util.getElementText(programNameHeading).contains("Program Name")
				&& (util.getElementText(programDescHeading).contains("Program Description"))
				&& (util.getElementText(programStatusHeading).contains("Program Status"))) {
			return true;
		}
		return false;

	}

	public boolean verifyDeleteBtnDisabled() {

		if (!util.isElementEnabled(deleteBtnManageProgram)) {
			return true;
		}
		return false;
	}

	public void search(String newProgram) {

		searchBox.clear();
		util.doClick(searchBox);
		log.info("Program to search>>" + (String) RunTimeData.getData("programName"));
		searchBox.sendKeys((String) RunTimeData.getData("programName"));

	}
	public void searchForEditDeleteProgram(String newProgram) {
		searchBox.clear();
		util.doClick(searchBox);
		searchBox.sendKeys(newProgram);

	}

	public void searchUpdatedProgram(String updatedProgram) {

		searchBox.clear();
		util.doClick(searchBox);
		searchBox.sendKeys(updatedProgram);

	}

	public String verifySearchResultProgramName() {
		String newProgram = (String) RunTimeData.getData("programName");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'" + newProgram + "')]")));

		WebElement result = driver.findElement(By.xpath("//td[contains(text(),'" + newProgram + "')]"));

		String resultText = result.getText();
		log.info("Search result >>" + resultText);
		log.info("Search result validation passed: " + resultText);
		return resultText;
	}

	public Map<String, String> verifyUpdatedProgramDetails() {

		String updatedProgram = (String) RunTimeData.getData("programNameEdit");
		String updatedProgramDesc = (String) RunTimeData.getData("programDescEdit");
		String updatedStatus = (String) RunTimeData.getData("programStatusEdit");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//td[contains(text(),'" + updatedProgram + "')]")));

		WebElement resultName = driver.findElement(By.xpath("//td[contains(text(),'" + updatedProgram + "')]"));
		WebElement resultDesc = driver.findElement(By.xpath("//td[contains(text(),'" + updatedProgramDesc + "')]"));
		WebElement resultStatus = driver.findElement(By.xpath("//td[contains(text(),'" + updatedStatus + "')]"));

		String resultNameText = resultName.getText();
		String resultDescText = resultDesc.getText();
		String resultStatusText = resultStatus.getText();

		Map<String, String> actualResultMap = new HashMap<>();
		actualResultMap.put("resultProgramNameText", resultNameText);
		actualResultMap.put("resultProgramDescText", resultDescText);
		actualResultMap.put("resultProgramStatusText", resultStatusText);

		log.info("Search result Name >>" + resultNameText);
		log.info("Search result Desc >>" + resultDescText);
		log.info("Search result validation passed: ");

		return actualResultMap;
	}

	public boolean verifySearchBarManageProgram(String searchBarText) {

		searchBox.isDisplayed();
		log.info("Search bar text -" + util.getAttributeVal(searchBar, "placeholder"));

		return util.getAttributeVal(searchBar, "placeholder").equalsIgnoreCase(searchBarText);

	}

	public boolean verifyCheckBoxUnchecked() {
		if (!util.getElement(checkBoxHeader).isSelected()) {
			log.info("Checkbox is unchecked");

			return true;
		} else {
			log.info("Checkbox is checked");
			return false;
		}

	}

	public boolean verifyFooterOfManageProgram() {

		WebElement paginationInfo = driver
				.findElement(By.xpath("//span[@class='p-paginator-current ng-star-inserted']"));
		String text = paginationInfo.getText(); // "Showing 1 to 10 of 50 entries"

		String[] words = text.split(" ");
		String totalEntries = words[words.length - 2];

		int totalPrograms = Integer.parseInt(totalEntries);

		String actualFooterText = util.getElementText(footerPrograms);
		String expectedText = "In total there are " + totalPrograms + " programs.";

		return actualFooterText.equals(expectedText);

	}

	public void clickOnNextPage() {
		util.clickElementByJS(nextPaginatorBtn, driver);

	}

	public boolean nextPageEnabled() {
		return util.isElementEnabled(nextPaginatorBtn);

	}

	public void clickOnLastPage() {
		util.clickElementByJS(lastPaginatorBtn, driver);

	}

	public boolean verifyNextPageBtnDisabled() {

		if (!util.isElementEnabled(nextPaginatorBtn)) {
			return true;
		}
		return false;
	}

	public void clickOnPreviuosPage() {
		util.clickElementByJS(prevPaginatorBtn, driver);

	}

	public boolean previousPageEnabled() {
		return util.isElementEnabled(prevPaginatorBtn);

	}

	public void clickOnFirstPage() {
		util.clickElementByJS(firstPaginatorBtn, driver);

	}

	public boolean verifyPreviousPageBtnDisabled() {

		if (!util.isElementEnabled(prevPaginatorBtn)) {
			return true;
		}
		return false;
	}

	public Map<String, String> verifyRequiredFieldErrorMessage() {

		List<WebElement> actualMsgs = driver.findElements(requiredFieldErrorMsgs);
		String progNameErrorMsg = actualMsgs.get(0).getText();
		String progDescErrorMsg = actualMsgs.get(1).getText();
		String statusErrorMsg = actualMsgs.get(2).getText();

		Map<String, String> actualErrorMsgsMap = new HashMap<>();
		actualErrorMsgsMap.put("progNameErrorMsg", progNameErrorMsg);
		actualErrorMsgsMap.put("progDescErrorMsg", progDescErrorMsg);
		actualErrorMsgsMap.put("statusErrorMsg", statusErrorMsg);

		return actualErrorMsgsMap;

	}

	public String verifyProgramNameAlreadyExistsErrorMessage(String expProgNameErrorMsg) {

		List<WebElement> actualMsgs = driver.findElements(requiredFieldErrorMsgs);
		String progNameExistErrorMsg = actualMsgs.get(0).getText();

		return progNameExistErrorMsg;

	}

	public void clickXProgramBtn() {
		util.doClick(xButton);

	}

	public void clickXDeleteConfirmationBtn() {
		util.doClick(xDeleteButton);

	}

	public boolean verifyZeroSearchResultsAfterDeletion() throws InterruptedException {

		Thread.sleep(500);
		List<WebElement> programResults = driver.findElements(programResultsTable);
		log.info("Results list size ---" + programResults.size());
		if (programResults.size() < 1)
			return true;

		return false;
	}

	public void clickProgramNameSort() {
		actions.click(util.getElement(programNameSort)).perform();
		actions.click(util.getElement(programNameSort)).perform();
	}

	public List<String> printWebElements(List<WebElement> options) {
		List<String> texts = new ArrayList<String>();
		int i = 0;
		for (WebElement option : options) {
			texts.add(i, option.getText());
			i++;
		}
		log.info("The number of items in the list are: " + texts.size());
		return texts;
	}

	// get and return original list
	public List<String> getOriginalList(String type) {
		List<String> originalList = null;

		if (type.equals("ProgramName")) {
			originalList = printWebElements(util.getElements(programNameList));

		} else if (type.equals("ProgramDescription")) {
			originalList = printWebElements(util.getElements(programDescriptionList));

		} else if (type.equals("ProgramStatus")) {
			originalList = printWebElements(util.getElements(programStatusList));

		}
		return originalList;
	}

	// this method will sort the given list
	public List<String> getSortedList(List<String> originalList) {
		log.info("Original List Before sorting is" + originalList);
		List<String> sortedList = new ArrayList<>(originalList);
		Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
		log.info("Sorted List After sorting is" + sortedList);
		return sortedList;
	}

	public void clickProgramNameSortDescend() {
		actions.click(util.getElement(programNameSort)).perform();
		actions.click(util.getElement(programNameSort)).perform();
		actions.click(util.getElement(programNameSort)).perform();

	}

	public List<String> getSortedListDescending(List<String> originalList) {

		log.info("Original List Before sorting is" + originalList);
		List<String> sortedList = new ArrayList<>(originalList);
		Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER.reversed());
		log.info("Sorted List After sorting is" + sortedList);
		return sortedList;
	}

	public void clickProgramDescriptionSort() {
		actions.click(util.getElement(programDescriptionSort)).perform();
		actions.click(util.getElement(programDescriptionSort)).perform();

	}

	public void clickProgramDescriptionSortDes() {
		actions.click(util.getElement(programDescriptionSort)).perform();
		actions.click(util.getElement(programDescriptionSort)).perform();
		actions.click(util.getElement(programDescriptionSort)).perform();

	}

	public void clickProgramStatusSort() {
		actions.click(util.getElement(programStatusSort)).perform();
		actions.click(util.getElement(programStatusSort)).perform();

	}

	public void clickProgramStatusSortDes() {
		actions.click(util.getElement(programStatusSort)).perform();
		actions.click(util.getElement(programStatusSort)).perform();
		actions.click(util.getElement(programStatusSort)).perform();

	}

	public boolean verifyErrorMessage() {

		boolean isErrorMessagePresent = util.isElementDisplayed(toastErrorMessage);

		if (isErrorMessagePresent) {
			String errorMessage = getErrorToast();
			log.info("Validation error displayed as expected: " + errorMessage);

		}
		return isErrorMessagePresent;

	}

}
