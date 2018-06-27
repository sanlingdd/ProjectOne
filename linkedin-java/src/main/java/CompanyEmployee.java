import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CompanyEmployee {

	public static void main(String[] args) throws InterruptedException {

		if (args.length == 0) {
			args[0] = "305250";
		}
		String company = "http://www.linkedin.com/search/results/people/?facetCurrentCompany=%5B%22" + args[0]
				+ "%22%5D&facetGeoRegion=%5B%22cn%3A8909%22%2C%22cn%3A8883%22%5D&origin=FACETED_SEARCH";
		PeopleYouMayKnow obj = new PeopleYouMayKnow();
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "/temp/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.linkedin.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		WebElement account = driver.findElements(By.xpath(".//input[@id='login-email']")).get(0);
		account.sendKeys("17612165703");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		WebElement pass = driver.findElement(By.xpath(".//input[@id='login-password']"));
		pass.sendKeys("Initial0");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		WebElement button = driver.findElement(By.xpath(".//input[@id='login-submit']"));
		button.click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		obj.sleep(1000);
		driver.get(company);
		obj.sleep(3000);

		while (true) {
			
			try {
				
				List<WebElement> nextPageElements = driver
						.findElements(By.xpath(".//button[@class='button-secondary-small']/span[text()='加为好友']"));
				if (nextPageElements.isEmpty()) {
					break;
				}
				
				List<WebElement> elements = driver
						.findElements(By.xpath(".//button[@class='button-secondary-small']/span[text()='加为好友']"));
				if (!elements.isEmpty()) {
					elements.get(0).click();
					driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
					Thread.sleep(10000);
					count++;
				} else {
					break;
				}
			} catch (Exception e) {
				break;
			}
		}

	}

}
