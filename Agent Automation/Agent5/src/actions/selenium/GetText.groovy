package actions.selenium
import actions.selenium.utils.GetObjectRepository
import actions.selenium.utils.Elements
import actions.selenium.Browser
import org.openqa.selenium.WebElement

class GetText{
  
  public String run(def params){
  //  WebElement element = Elements.find(params,Browser.Driver)
      
//New Code
   String ElementName = (String) params.get("Element Name");
   String PageName = (String) params.get("Page Name"); 
   WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
//New Code      
      
    
    return element.getText()
  }
}