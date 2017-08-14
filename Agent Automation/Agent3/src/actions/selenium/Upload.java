package actions.selenium;
import actions.selenium.utils.Element;
//import actions.selenium.utils.GetObjectRepository
import java.util.*;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


class Upload{
       static WebDriver driver;
    public void run(HashMap<String, Object> params)throws IOException, InterruptedException{
     
        String URL =null;
       // WebElement element = new Element().find(params,Browser.Driver);
       // WebElement element = driver.findElement(By.name("uploadsubmit"));
       // element.sendKeys("D:/AutoIT/SciTE/FILEUPLOAD.exe");
		//element.click();
               //Which calls the autoit exe file
        //  WebElement element = GetObjectRepository.find_Element(ElementName,PageName)
        String Location =(String)params.get("Location");
		Runtime.getRuntime().exec(Location);
        
    }
}