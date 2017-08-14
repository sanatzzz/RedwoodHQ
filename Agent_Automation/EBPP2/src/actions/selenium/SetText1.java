package actions.selenium;
import actions.selenium.utils.Element;
import actions.selenium.Browser;
import actions.selenium.App;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import java.util.*;

class SetText1{
  
  public void run(HashMap<String, Object> params){
      System.out.println("Inside Set Text");
    WebElement element = new Element().find(params,Browser.Driver);
    element.clear();
    element.sendKeys(params.get("Text").toString()) ;   
    
    
  }
}