package actions.selenium
import actions.selenium.utils.GetObjectRepository
import actions.selenium.Browser
import org.openqa.selenium.WebElement
import org.openqa.selenium.Keys

class SetText{
  
  public static void run(def params){
   // WebElement element = Elements.find(params,Browser.Driver)
       String ElementName = (String) params.get("Element Name");
   String PageName = (String) params.get("Page Name"); 
   WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
    element.clear()
    element.sendKeys(params."Text")    
    
    
  }
}