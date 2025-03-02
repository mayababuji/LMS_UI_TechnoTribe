package stepDefinitions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import hooks.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.CommonPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProgramPage;
import utilities.Log;
import utilities.ReadConfig;
import utilities.RunTimeData;

public class ProgramStepDef {

	WebDriver driver;
	TestContext context;
	ReadConfig readConfig;
	LoginPage loginPage;
	HomePage homePage;
	ProgramPage programPage;
	CommonPage commonPage;

	public static Map<String, String> programData;

	public ProgramStepDef(TestContext context) {
		this.context = context;
		this.driver = context.getDriver();
		this.readConfig = new ReadConfig();
		programPage = new ProgramPage(driver);
		homePage = new HomePage(driver);
	}

	@Then("Admin should be navigated to Program page")
	public void admin_should_be_navigated_to_program_page() {
		Assert.assertEquals(programPage.getProgramPageTitle(), "Manage Program");
	}

	@Then("Admin should see Logout in menu bar")
	public void admin_should_be_see_Logout_on_MenuBar() {
		programPage.isLogoutDisplayedMenuBar();
	}

	@Then("Admin should see the heading {string}")
	public void admin_should_be_see_heading_LMS(String LMSHeader) {
		Assert.assertEquals(programPage.getLMSHeaderMenuBar(), LMSHeader);
	}

	@Then("Admin should see sub menu in menu bar as {string}")
	public void admin_should_see_the_subMenu(String AddNewProgramSubMenu) {
		Assert.assertEquals(programPage.getAddNewProgramSubMenu(), AddNewProgramSubMenu);
	}

	@Then("Admin should see the Manage Program {string} Heading")
	public void admin_should_see_the_title(String manageProgramTitle) {
		Assert.assertEquals(programPage.getManageProgramText(), manageProgramTitle);
	}

	@Given("Admin is on program details form")
	public void admin_is_on_program_details_form() {

		programPage.clickAddNewProgramBtn();
	}

	@When("Admin clicks Add New Program under Program menu bar")
	public void admin_clicks_add_new_program_under_program_menu_bar() {
		programPage.clickAddNewProgramBtn();
	}

	@Then("Admin should see pop up window for program details")
	public void admin_should_see_pop_up_window_for_program_details() {

		Assert.assertEquals(programPage.isAddNewProgramPopUpDisplayed(), true);
	}

	@Given("Admin is on Program details form")
	public void admin_is_on_pop_up_window_for_add_new_program() throws Exception {

		programPage = (ProgramPage) homePage.selectOptionNavigationMenuBar("Program");
		programPage.clickAddNewProgramBtn();
	}

	@When("Admin clicks save button without entering mandatory fields")
	public void admin_clicks_save_button_withoutMandatoryFields() {
		programPage.clickSaveBtn();
	}

	@Then("Admin should see window title as {string}")
	public void admin_should_see_windowTitle_program_details(String expWindowTitle) {
		Assert.assertEquals(programPage.getAddNewProgramPopUpTitle(), expWindowTitle);
	}

	@Then("Admin gets message for mandatory field required")
	public void admin_gets_message_for_mandatoryField() {

		Map<String, String> resultErrorMsgMap = programPage.verifyRequiredFieldErrorMessage();

		Assert.assertEquals(resultErrorMsgMap.get("progNameErrorMsg"), "Program name is required.");
		Assert.assertEquals(resultErrorMsgMap.get("progDescErrorMsg"), "Description is required.");
		Assert.assertEquals(resultErrorMsgMap.get("statusErrorMsg"), "Status is required.");
	}

	@Then("Admin can see Program Details form disappears")
	public void admin_sees_manageProgram_Page() {
		Assert.assertEquals(programPage.getProgramPageTitle(), "Manage Program");
	}

	@When("Admin clicks Cancel button")
	public void admin_clicks_cancel_button() {
		programPage.clickCancelProgramBtn();
	}

	@When("Admin clicks X button on pop up window")
	public void admin_clicks_X_button() {
		programPage.clickXProgramBtn();
	}

	@When("Admin enters details for {string} for mandatory fields and Click on save button")
	public void admin_enters_details_for_for_mandatory_fields_and_click_on_save_button(String testCase)
			throws Exception {

		programPage.fillProgramForm(testCase.trim());

	}

	@Then("Admin gets message {string}")
	public void admin_gets_message(String expSuccessMsg) {
		Assert.assertEquals(programPage.getToast(), "Successful");

	}

	@Then("Admin gets error message")
	public void admin_gets_ErrorMessage() {
		Assert.assertEquals(programPage.verifyErrorMessage(), true);

	}

	@Then("Admin gets error message for existing Program name on the Program Details Pop up")
	public void admin_gets_error_message_on_existing_ProgName() {
		Assert.assertEquals(programPage.getErrorMessage(), "Program name is already exist.");
	}

	@Then("Admin gets error message on the Program Details Pop up")
	public void admin_gets_error_message_on_the_ProgramDetails_mandatory_field() {
		Assert.assertEquals(programPage.getErrorMessage(), "Description is required.");
	}

	@Then("Admin gets error message for blank Program Name on the Program Details Pop up")
	public void admin_gets_error_message_on_the_ProgramDetails_ProgramName() {
		Assert.assertEquals(programPage.getErrorMessage(), "Program name is required.");
	}

	@Then("Admin gets error message for blank Status on the Program Details Pop up")
	public void admin_gets_error_message_on_the_ProgramDetails_ProgramStatus() {
		Assert.assertEquals(programPage.getErrorMessage(), "Status is required.");
	}

	@Then("Admin sees error message for invalid Program Name on the Program Details Pop up")
	public void admin_gets_error_message_on_the_ProgramDetails_invalidProgramName() {
		Assert.assertEquals(programPage.getErrorMessage(),
				"This field should start with an alphabet, no special char and min 2 char.");
	}

	@Then("Admin sees error message for invalid Program Description on the Program Details Pop up")
	public void admin_gets_error_message_on_the_ProgramDetails_invalidProgramDesc() {
		Assert.assertEquals(programPage.getErrorMessage(),
				"This field should start with an alphabet, no special char and min 2 char.");
	}

	@Then("Admin should see the page names as in order on menu bar")
	public void admin_sees_menuBar() {
		programPage.menuBarDisplay();
	}

	@Then("Admin should see the title on Program page {string}")
	public void admin_sees_lms_titleOnProgPage() {
		programPage.menuBarDisplay();
	}

	@Then("Admin should able to see Program name, description, and status for each program")
	public void admin_should_be_able_to_see_table_with_program_name_description_and_status_headings() {
		Assert.assertEquals(programPage.verifyColumnHeader(), true);
	}

	@Then("Admin should see a Delete button in left top is disabled")
	public void delete_Btn_should_be_disabled() {
		Assert.assertEquals(programPage.verifyDeleteBtnDisabled(), true);
	}

	@Given("Admin is on Program page")
	public void admin_is_on_program_page() throws Exception {

		programPage = (ProgramPage) homePage.selectOptionNavigationMenuBar("Program");
	}

	@When("Admin searches with newly created Program {string}")
	public void admin_searches_with_newly_updated(String newProgName) {

		programPage.search(newProgName);
	}

	@Then("Records of the newly created  program is displayed and match the data entered")
	public void admin_verifies_that_the_details_are_correctly_updated() {

		Assert.assertEquals(programPage.verifySearchResultProgramName(), (String) RunTimeData.getData("programName"),
				"Searched Program Name does not match the result!");

	}

	@When("Admin edits the program name and click on save button for {string}")
	public void admin_edits_the_program_name_and_click_on_save_button(String testCase) throws InterruptedException {

		programPage.editTheProgramAndClickSave(testCase);

	}

	@When("Admin clicks on Edit option for particular program name {string}")
	public void admin_clicks_the_edit_for_program(String newProgram) {

		programPage.searchForEditDeleteProgram(newProgram);
		programPage.clickEditProgramBtn(newProgram);

	}

	@Then("Updated program Name and Desc and Status is seen by the Admin")
	public void updated_program_name_and_desc_and_status_is_seen_by_the_admin() {

		programPage.searchUpdatedProgram((String) RunTimeData.getData("programNameEdit"));

		Map<String, String> resultaMap = programPage.verifyUpdatedProgramDetails();

		Assert.assertEquals((String) RunTimeData.getData("programNameEdit"), resultaMap.get("resultProgramNameText"),
				"Searched Program Name does not match the result!");
		Assert.assertEquals((String) RunTimeData.getData("programDescEdit"), resultaMap.get("resultProgramDescText"),
				"Searched Program Desc does not match the result!");
		Assert.assertEquals((String) RunTimeData.getData("programStatusEdit"),
				resultaMap.get("resultProgramStatusText"), "Searched Program Status does not match the result!");
	}

	@Then("Admin should see Search bar with text as {string}")
	public void admin_should_see_search_bar_with_text_as(String searchBarText) {

		Assert.assertEquals(programPage.verifySearchBarManageProgram(searchBarText), true);

	}

	@Then("Admin should see data table with column header on the Manage Program Page as  Program Name, Program Description, Program Status, Edit\\/Delete")
	public void admin_should_see_data_table_with_column_header_on_the_manage_program_page_as_program_name_program_description_program_status_edit_delete() {
		List<String> expectedHeaders = Arrays.asList("Program Name", "Program Description", "Program Status",
				"Edit/Delete");
		Assert.assertEquals(programPage.verifyTableHeaders(expectedHeaders), true);
	}

	@Then("Admin should see checkbox default state as unchecked beside Program Name column header")
	public void admin_should_see_checkbox_default_state_as_unchecked_beside_program_name_column_header() {

		Assert.assertEquals(programPage.isElementPresent("checkbox", "header"), true);
		Assert.assertEquals(programPage.verifyCheckBoxUnchecked(), true);
	}

	@Then("Admin should see check box default state as unchecked on the left side in all rows against program name")
	public void admin_should_see_check_box_default_state_as_unchecked_on_the_left_side_in_all_rows_against_program_name() {

		Assert.assertEquals(programPage.isElementPresent("checkbox", "row"), true);
	}

	@Then("Admin should see the sort arrow icon beside to each column header except Edit and Delete")
	public void admin_should_see_the_sort_arrow_icon_beside_to_each_column_header_except_edit_and_delete() {

		Assert.assertEquals(programPage.isElementPresent("sort-icon", "header"), true);
	}

	@Then("Admin should see the Edit and Delete buttons on each row of the data table")
	public void admin_should_see_the_edit_and_delete_buttons_on_each_row_of_the_data_table() {

		Assert.assertEquals(programPage.isElementPresent("edit-icon", "row"), true);
		Assert.assertEquals(programPage.isElementPresent("delete-icon", "row"), true);
	}

	@Then("Admin should see the text along with Pagination icon below the table")
	public void admin_should_see_entries_text_with_pagination() {
		Assert.assertEquals(programPage.isPaginationAvailable(), true);
	}

	@Then("Admin should see the footer with total programs")
	public void admin_should_see_the_footer_as() {

		Assert.assertEquals(programPage.verifyFooterOfManageProgram(), true);
	}

	@When("Admin clicks on delete button for a program {string}")
	public void admin_clicks_on_delete_button_for_a_program(String programName) {
		programPage.clickDeleteProgramBtn(programName);
	}

	@Then("Admin will get confirm deletion popup")
	public void admin_will_get_confirm_deletion_popup() {

		Assert.assertEquals(programPage.verifyDeleteProgramPopUp(), true);
	}

	@Given("Admin is on Confirm deletion form for program {string}")
	public void admin_is_on_confirm_deletion_form(String programName) throws Exception {

		programPage = (ProgramPage) homePage.selectOptionNavigationMenuBar("Program");
		programPage.searchUpdatedProgram((String) RunTimeData.getData("programNameEdit"));
		programPage.clickDeleteProgramBtn((String) RunTimeData.getData("programNameEdit"));
	}

	@When("Admin clicks on Yes button")
	public void admin_clicks_on_button() throws Exception {
		programPage.clickDeleteButtons("yes");

	}

	@Then("Admin can see {string} message")
	public void admin_can_see_message(String ExpDeleteSuccessMsg) {
		Assert.assertEquals(programPage.getToast(), "Successful");

	}

	@When("Admin clicks on No button")
	public void admin_clicks_on_No_button() throws Exception {

		programPage.clickDeleteButtons("no");
	}

	@When("Admin clicks on close X button")
	public void admin_clicks_X_button_delete() throws Exception {
		Assert.assertEquals(programPage.verifyDeleteProgramPopUp(), true);
		programPage.clickDeleteButtons("close");
	}

	@Then("Admin can see Confirm deletion form disappears")
	public void admin_sees_manageProgram_Page_AfterNoDelete() {
		Assert.assertEquals(programPage.getProgramPageTitle(), "Manage Program");
	}

	@When("Admin Searches for {string}")
	public void admin_searches_deleted_Program(String deletedProgramName) {
		programPage.searchForEditDeleteProgram(deletedProgramName);
	}

	@Then("There should be zero results")
	public void admin_sees_NoPrograms_AfterDelete() throws InterruptedException {
		Assert.assertEquals(programPage.verifyZeroSearchResultsAfterDeletion(), true);
	}

	@When("Admin selects more than one program by clicking on the checkbox")
	public void admin_selects_more_than_one_program_by_clicking_on_the_checkbox() {

		programPage.storeBeforeCount();
		programPage.selectCheckboxes(2);
	}

	@When("Admin clicks on {string} button")
	public void admin_clicks_on_button(String string) throws InterruptedException {
		programPage.clickYesDeleteBtn();
	}

	@When("Admin clicks on the delete button on the left top of the program page")
	public void admin_clicks_on_the_delete_button_on_the_left_top_of_the_program_page() {
		programPage.selectCheckboxes(4);
		programPage.clickdeleteAllButton();

	}
	
	@When("Admin clicks Next page link on the program table")
	public void admin_clicks_next_page_link_on_the_program_table() {
	    programPage.clickOnNextPage();
	}
	@Then("Admin should see the Pagination has {string} active link")
	public void admin_should_see_the_pagination_has_active_link(String string) {
	    boolean nextPageActive = programPage.nextPageEnabled();
	    Assert.assertTrue(nextPageActive);
	    
	}
	
	@When("Admin clicks Last page link")
	public void admin_clicks_last_page_link() {
	    programPage.clickOnLastPage();
	}

	@Then("Admin should see the last page record on the table with Next page link are disabled")
	public void admin_should_see_the_last_page_record_on_the_table_with_next_page_link_are_disabled() {
		boolean nextPageDisabled = programPage.verifyNextPageBtnDisabled();
	    Assert.assertTrue(nextPageDisabled);
	}
	
	@Given("Admin is on last page of Program page table")
	public void admin_is_on_last_page_of_program_page_table() throws Exception {
		programPage = (ProgramPage) homePage.selectOptionNavigationMenuBar("Program");
		 programPage.clickOnLastPage();
	}

	@When("Admin clicks Previous page link")
	public void admin_clicks_previous_page_link() {
	    programPage.clickOnPreviuosPage();
	}

	@Then("Admin should see the previous page record on the table with pagination has previous page link")
	public void admin_should_see_the_previous_page_record_on_the_table_with_pagination_has_previous_page_link() {
		boolean prevoiusPageActive = programPage.previousPageEnabled();
	    Assert.assertTrue(prevoiusPageActive);
	}
	
	@Given("Admin is on Previous Program page")
	public void admin_is_on_previous_program_page() throws Exception {
		programPage = (ProgramPage) homePage.selectOptionNavigationMenuBar("Program");
		 programPage.clickOnLastPage();
		 programPage.clickOnPreviuosPage();
	}

	@When("Admin clicks First page link")
	public void admin_clicks_first_page_link() {
	    programPage.clickOnFirstPage();
	}

	@Then("Admin should see the very first page record on the table with Previous page link are disabled")
	public void admin_should_see_the_very_first_page_record_on_the_table_with_previous_page_link_are_disabled() {
		boolean previousPageActive = programPage.verifyPreviousPageBtnDisabled();
	    Assert.assertTrue(previousPageActive);
	}

	@When("Admin clicks on Arrow next to Program Name of Program module page for sort ascending")
	public void admin_clicks_on_arrow_next_to_program_name_of_program_module_page_for_sort_ascending() {
		programPage.clickProgramNameSort();
	}

	@Then("Admin See the Program Name is sorted in ascending order")
	public void admin_see_the_program_name_is_sorted_in_ascending_order() {
		List<String> originalList = programPage.getOriginalList("ProgramName");
		List<String> sortedList = programPage.getSortedList(originalList);
		Log.logInfo("sorted name list" + sortedList.toString() );
		Assert.assertTrue(originalList.equals(sortedList));
}
	@When("Admin clicks on Arrow next to Program Name of Program module page for sort descend")
	public void admin_clicks_on_arrow_next_to_program_name_of_program_module_page_for_sort_descend() {
		programPage.clickProgramNameSortDescend();
	}
	
	@Then("Admin See the Program Name is sorted in descending order")
	public void admin_see_the_program_name_is_sorted_in_descending_order() {
		List<String> originalList = programPage.getOriginalList("ProgramName");
		List<String> sortedList = programPage.getSortedListDescending(originalList);
		Log.logInfo("Descending sorted name list " + sortedList.toString());
		Assert.assertTrue(originalList.equals(sortedList));
	}
	
	@When("Admin clicks on Arrow next to program description of Program module page for sort ascending")
	public void admin_clicks_on_arrow_next_to_program_description_of_program_module_page_for_sort_ascending() {
	    programPage.clickProgramDescriptionSort();
	}

	@Then("Admin See the program description is sorted Ascending order in Program module page")
	public void admin_see_the_program_description_is_sorted_ascending_order_in_program_module_page() {
		List<String> originalList = programPage.getOriginalList("ProgramDescription");
		List<String> sortedList = programPage.getSortedList(originalList);
		Log.logInfo("sorted name list" + sortedList.toString());
		Assert.assertTrue(originalList.equals(sortedList));
	}
	
	@When("Admin clicks on Arrow next to program description of Program module page for sort descending")
	public void admin_clicks_on_arrow_next_to_program_description_of_program_module_page_for_sort_descending() {
		programPage.clickProgramDescriptionSortDes();
	}

	@Then("Admin See the program description is sorted Descending order in Program module page")
	public void admin_see_the_program_description_is_sorted_descending_order_in_program_module_page() {
		List<String> originalList = programPage.getOriginalList("ProgramDescription");
		List<String> sortedList = programPage.getSortedListDescending(originalList);
		Log.logInfo("Descending sorted name list " + sortedList.toString());
		Assert.assertTrue(originalList.equals(sortedList));
	}
	
	@When("Admin clicks on Arrow next to program Status of Program module page for sort ascending")
	public void admin_clicks_on_arrow_next_to_program_status_of_program_module_page_for_sort_ascending() {
		 programPage.clickProgramStatusSort();
	}

	@Then("Admin See the program Status is sorted Ascending order in Program module page")
	public void admin_see_the_program_status_is_sorted_ascending_order_in_program_module_page() {
		List<String> originalList = programPage.getOriginalList("ProgramStatus");
		List<String> sortedList = programPage.getSortedList(originalList);
		Log.logInfo("sorted name list" + sortedList.toString());
		Assert.assertTrue(originalList.equals(sortedList));
	}
	
	@When("Admin clicks on Arrow next to program Status of Program module page for sort descending")
	public void admin_clicks_on_arrow_next_to_program_status_of_program_module_page_for_sort_descending() {
		 programPage.clickProgramStatusSortDes();
	}

	@Then("Admin See the program Status is sorted descending order in Program module page")
	public void admin_see_the_program_status_is_sorted_descending_order_in_program_module_page() {
		List<String> originalList = programPage.getOriginalList("ProgramStatus");
		List<String> sortedList = programPage.getSortedListDescending(originalList);
		Log.logInfo("sorted name list" + sortedList.toString());
		Assert.assertTrue(originalList.equals(sortedList));
	}
	


}
