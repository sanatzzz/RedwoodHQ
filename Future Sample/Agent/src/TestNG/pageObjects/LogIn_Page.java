package TestNG.pageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
 
 public class LogIn_Page {
 
    private static WebElement element = null;
 
    public static WebElement txtbox_Username(WebDriver driver){
 
    element = driver.findElement(By.xpath("//*[@id=\"j_id__ctru12:r1:0:s1:ot189::content\"]"));
 
    return element;
 
    }
 
     public static WebElement txtbox_Password(WebDriver driver){
 
    element = driver.findElement(By.xpath("//*[@id=\"j_id__ctru12:r1:0:s1:it587::content\"]"));
 
 return element;
 
    }
 
     public static WebElement btn_Login(WebDriver driver){
	 
	    element = driver.findElement(By.xpath("//*[@id=\"j_id__ctru12:r1:0:s1:cb1\"]"));
	 
	 return element;
	 
	    }
 
}