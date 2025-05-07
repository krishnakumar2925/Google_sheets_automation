package Levich.google_sheets.abstractComponents;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {
	WebDriver driver;
	WebDriverWait wait;
	
	public SeleniumUtils(WebDriver driver) {
		this.driver =driver;
		this.wait=new WebDriverWait(driver,Duration.ofSeconds(30));
	}
	
	public boolean click(WebElement element) {
		try {
			if (element.isDisplayed() && element.isEnabled()) {
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",element);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			return true;
			}
			
				
		}
		catch(Exception e) {
			System.out.println("Click Failed "+e.getMessage());
		}
		return false;
		
		
	}
	
	public boolean sendkeys(WebElement element,String message) {
		try{
					wait.until(ExpectedConditions.and(
				    ExpectedConditions.elementToBeClickable(element),
				    ExpectedConditions.visibilityOf(element)
				));
			element.clear();
			element.sendKeys(message);
			return true;
			
		}catch(Exception e) {
			System.out.println("Sendkey Failed "+e.getMessage());
		}
		return false;
		
		
	}
	
	public boolean navigateToURL(String URL) {
		try {
			String currentUrl  = driver.getCurrentUrl();
			if(!(currentUrl.equals(URL)||currentUrl.isEmpty())){
				driver.get(URL);
				return true;
			}
		}catch(Exception e) {
			System.out.println("Navigation Failed "+e.getMessage());
		}
		return false;
	
	
}
public void WaitForURL(WebDriver driver,String Url){
    WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    wait.until(ExpectedConditions.urlContains(Url));
    
}
}

