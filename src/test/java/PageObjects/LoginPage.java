package PageObjects;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	WebDriver driver;
	
	
	@FindBy(how=How.CSS, using = "input#user")
	private WebElement username;
	
	@FindBy(how=How.CSS, using = "input#password")
	private WebElement password;
	
	@FindBy(how=How.CSS, using = "input#login")
	private WebElement loginButton;
	
	@FindBy(how=How.CSS, using = "button#login-submit")
	private WebElement loginSubmitButton;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getHomePageUrl() {
		return driver.getCurrentUrl();
	}
	
	public void sendUsername(String userName) {
		username.sendKeys(userName);
	}
	
	public void sendPassword(String passWord) {
		password.sendKeys(passWord);
	}
	
	public void waitForThePasswordToAppear(WebElement element) {
		new WebDriverWait(driver, 5000).until(ExpectedConditions.visibilityOf(element));
	}
	
	public void isPageLoaded() {
		waitForThePasswordToAppear(password);
	}
	
	public void isUsernameVisible() {
		waitForThePasswordToAppear(username);
	}
	public void clickLoginButton() {
		new WebDriverWait(driver, 5000).until(ExpectedConditions.invisibilityOf(password));
		loginButton.click();
	}
	
	public void clickLoginSubmitButton() {
		loginSubmitButton.click();
	}
	
	public void verifyMainPageUrl() {
		 String expectedUrl = driver.getCurrentUrl();
		 assertEquals(driver.getCurrentUrl(), expectedUrl, "The url of page is not correct");
	}
	
}
