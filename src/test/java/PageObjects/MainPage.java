package PageObjects;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {


	WebDriver driver;
	
	@FindBy(how=How.CSS, using = "div.float-right.buttons>a:nth-child(1)")
	private WebElement loginButton;
	
	public MainPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void goToMainPage() {
		driver.get("https://trello.com/");
	}
	
	public void verifyMainPageUrl() {
		 String expectedUrl = driver.getCurrentUrl();
		 assertEquals(driver.getCurrentUrl(), expectedUrl, "The url of page is not correct");
	}
	
	public void clickLoginButton() {
		loginButton.click();
	}
	
	
	
}
