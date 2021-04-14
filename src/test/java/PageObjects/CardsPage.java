package PageObjects;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CardsPage {

	WebDriver driver;
	

	@FindBy(how = How.CSS, using = "div#board>div:nth-child(1)>div>div")
	private List<WebElement> itemsInTheList;

	@FindBy(how = How.CSS, using = "div#board>div:nth-child(1)>div>div")
	private WebElement v1;
	
	@FindBy(how = How.CSS, using = "header._3ZtNW03Xco1Lte > div._2Un9i9htRmbUrY")
	private WebElement switchViewCss;
	
	@FindBy(how = How.CSS, using = "header._3ZtNW03Xco1Lte > button > span")
	private WebElement switchViewCloseButton;
	
	@FindBy(how = How.CSS, using = "#content > div > div.board-menu.js-fill-board-menu > div > div > div.board-menu-header.js-board-menu-title.no-transition.is-board-menu-default-view.is-in-frame > div")
	private WebElement menuHeaderPopUp;
	
	@FindBy(how = How.CSS, using = "#content > div > div.board-menu.js-fill-board-menu > div > div > div.board-menu-header.js-board-menu-title.no-transition.is-board-menu-default-view.is-in-frame > div > a.board-menu-header-close-button.icon-lg.icon-close.js-hide-sidebar")
	private WebElement menuHeaderCloseButton;
	
	
	@FindBy(how = How.CSS, using = "div#board>div:nth-child(1)>div>div:nth-child(2)>div.card-composer>div.list-card.js-composer > div > textarea")
	private WebElement textBoxForCard;
	
	@FindBy(how = How.CSS, using = "div#board>div:nth-child(1)>div>div:nth-child(2)>div.card-composer>div.cc-controls.u-clearfix>div.cc-controls-section>input")
	private WebElement addCardButton;
	
	@FindBy(how = How.CSS, using = "#board > div:nth-child(1) > div > div.card-composer-container.js-card-composer-container > a > span.js-add-another-card")
	private WebElement addAnotherCard;
	
	
	@FindBy(how = How.CSS, using = "div#surface > div >div.js-react-root >div#header >div:nth-child(3) >button:nth-child(3) > div")
	private WebElement accountName;
	
	@FindBy(how = How.CSS, using = "body > div.atlaskit-portal-container > div > section > div > nav > ul > li:nth-child(10) > button")
	private WebElement logOutButton;

	public CardsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	public void verifyCardsPageUrl(String boardname) {
		String currentUrl = driver.getCurrentUrl();
		boolean flag = false;
		if (currentUrl.contains(boardname.toLowerCase()))
			flag = true;
		System.out.println("cards page url = " + driver.getCurrentUrl());
	}
	

	public void createCard(String cardName) throws InterruptedException {
		int sizeOfExistingCards = 0;
		System.out.println("c1");
		
		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfAllElements(itemsInTheList));


		System.out.println("itemsList Size = " + itemsInTheList.size());
		for (int i = 0; i < itemsInTheList.size(); i++) {
			
			if(i==1) {
				if(!itemsInTheList.get(1).findElements(By.cssSelector("a")).get(0).isDisplayed()) {
					if(menuHeaderPopUp.isDisplayed()) {
						new Actions(driver).moveToElement(menuHeaderCloseButton).click().build().perform();
					}
				}
				new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfAllElements(itemsInTheList.get(1).findElements(By.cssSelector("a"))));
				
				List<WebElement> existingCardsList = itemsInTheList.get(1).findElements(By.cssSelector("a"));
				
				sizeOfExistingCards = existingCardsList.size();
				System.out.println("sizeOfExistingCards = " + sizeOfExistingCards);
			}
			if (i == 2) {
				WebElement addAnotherCard = itemsInTheList.get(i).findElement(By.cssSelector("a"));
				
				addAnotherCard.click();
				textBoxForCard.sendKeys(cardName);
				addCardButton.click();
			}
		}

		System.out.println("Proceeding for validation of card added");
		driver.navigate().refresh();
		Thread.sleep(2500);
		/**Have to give this as the new card added adds with class attribute as "list-card js-member-droppable active-card ui-droppable"**
			**and takes 3-5 seconds to change to the class value for existing cards***/

		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='board']/div[1]/div/div[3]/a/../preceding-sibling::div[1]/a")));

		if(!addAnotherCard.isDisplayed()) {
			if(menuHeaderPopUp.isDisplayed()) {
				new Actions(driver).moveToElement(menuHeaderCloseButton).click().build().perform();
			}

		}
		List<WebElement> updatedCardsList = itemsInTheList.get(1)
				.findElements(By.cssSelector("a.list-card.js-member-droppable.ui-droppable"));
		System.out.println("updated card list size = " + updatedCardsList.size());
		int expectedCardListSize = sizeOfExistingCards + 1;
		int actualCardListSize = updatedCardsList.size();
		for(int k=0; k<updatedCardsList.size();k++) {
			System.out.println("updated cards class name = " + updatedCardsList.get(k).getAttribute("class"));
		}

		WebElement newAddedCard = updatedCardsList.get(actualCardListSize - 1)
				.findElement(By.cssSelector("div.list-card-details.js-card-details > span"));
		assertEquals(newAddedCard.getText(), cardName, "Card not added correctly");
		assertEquals(actualCardListSize, expectedCardListSize, "Card not added correctly");
		
	}
}
