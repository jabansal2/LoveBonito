package PageObjects;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.CSS, using = "div#chrome-container>div:nth-child(1)>  div:nth-child(1)   > div > div:nth-child(2) > div  > div:nth-child(2) > div")
	private List<WebElement> allWorkSpaces;

	@FindBy(how = How.CSS, using = "//*[@id=\"chrome-container\"]/div[1]/div[1]/div/div[2]/div/div[1]/div[2]")
	private List<WebElement> allWorkSpacesxpath;
	
	@FindBy(how = How.CSS, using = "div#header>div>a+button>span>span")
	private WebElement boards;

	@FindBy(how = How.CSS, using = "div#chrome-container >  div:nth-child(1)   >  div:nth-child(1) > div > div:nth-child(2) > div  > div:nth-child(2) > div:nth-child(3) > div:nth-child(2) > div:nth-child(2) > div")
	private List<WebElement> allBoards;

	public void verifyHomePageUrl() {
		String currentUrl = driver.getCurrentUrl();
		boolean flag = false;
		if (currentUrl.contains("boards")) {
			flag = true;
			System.out.println("home flag = " + flag);
		}
		
		System.out.println("home page url = " + driver.getCurrentUrl());
	}

	public void clickFirstBoard() {
		new WebDriverWait(driver, 5000).until(ExpectedConditions.elementToBeClickable(boards)).click();
	}

	public void getAllBoards(String strWorkspace, String str) {
		System.out.println("All Boards = " + allBoards);
		System.out.println("Number of Boards = " + allBoards.size());

		// click on board
		new WebDriverWait(driver, 5000).until(ExpectedConditions.visibilityOfAllElements(allWorkSpaces));
		int count = 0;
loop:	for (int i= 0; i<allWorkSpaces.size(); i++) {
			List<WebElement> workspacesGrandChild = allWorkSpaces.get(i).findElement(By.cssSelector("div:nth-child(1)")).findElements(By.tagName("a"));
			System.out.println("workspacessize = " + workspacesGrandChild.size());
			for(WebElement el: workspacesGrandChild) {
				System.out.println("el tagname = " + el.getTagName());
				
					WebElement workspace = allWorkSpaces.get(i).findElement(By.cssSelector("div>a>span>span:nth-child(2)"));
					System.out.println("Span class = " + workspace.getText());
					if (workspace.getText().equalsIgnoreCase(strWorkspace)) {
						List<WebElement> boardsInWorkSpace = new ArrayList<WebElement>();
						List<WebElement> temp1 = el.findElements(By.cssSelector("div:nth-child(2)>div"));
						for(int a=0; a<temp1.size(); a++) {
							System.out.println("temp1 class = " + temp1.get(a).getAttribute("class"));
						}
						List<WebElement> temp = el.findElements(By.xpath("../following-sibling::div/child::div"));
						if(temp.size() == 3){
							boardsInWorkSpace = allWorkSpaces.get(i)
									.findElements(By.cssSelector("div:nth-child(2) > div:nth-child(3)>div"));
						}
						if(temp.size() == 2) {
							boardsInWorkSpace = allWorkSpaces.get(i)
								.findElements(By.cssSelector("div:nth-child(2) > div:nth-child(2)>div"));
						}
						System.out.println("boardsInWorkSpace size = " + boardsInWorkSpace.size());
						for (WebElement board : boardsInWorkSpace) {
							
							System.out.println("inside boards arrayList");
							WebElement childBoard = board.findElement(By.cssSelector("a"));
							System.out.println("board title = " + childBoard.getAttribute("title"));
							String boardName = board.findElement(By.cssSelector("a > div:nth-child(3) > div")).getText();

							if (boardName.equalsIgnoreCase(str)) {
								System.out.println("boardName = " + boardName);
								board.click();
								break loop;
							}

						}
					}
//				}
			}


		}

	}

}
