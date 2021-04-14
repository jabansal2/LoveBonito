package TestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;

import PageObjects.CardsPage;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.MainPage;

public class TestCases {

	WebDriver driver;

	resources.ReadTestDataFromExcel readData = new resources.ReadTestDataFromExcel();

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) {
		if (browser.equalsIgnoreCase(("firefox"))) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/webdrivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}

		if (browser.equalsIgnoreCase("headless")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "/webdrivers/geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			driver = new FirefoxDriver(options);
		}
	}

	@DataProvider(name = "data-provider")
	public Object[][] checkexcelData() throws FilloException {
		Object[][] testdata = readData.testFillo();
		return testdata;

	}


	@Test(dataProvider = "data-provider")
	public void testMainPage(String username, String password, String workspace, String boardName, String cardName)
			throws InterruptedException {

		MainPage mainPage = new MainPage(driver);
		mainPage.goToMainPage();
		mainPage.verifyMainPageUrl();
		mainPage.clickLoginButton();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.verifyMainPageUrl();
		loginPage.isUsernameVisible();
		loginPage.sendUsername(username);

		loginPage.clickLoginButton();
		loginPage.isPageLoaded();
		loginPage.sendPassword(password);
		loginPage.clickLoginSubmitButton();

		HomePage homePage = new HomePage(driver);
		homePage.clickFirstBoard();
		homePage.getAllBoards(workspace, boardName);
		
		CardsPage cardsPage = new CardsPage(driver);
		cardsPage.createCard(cardName);
	}

	@AfterMethod
	public void clean() {
		driver.close();
	}

}
