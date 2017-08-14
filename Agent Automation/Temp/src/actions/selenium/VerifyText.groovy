package actions.selenium

import actions.selenium.utils.Elements
import actions.selenium.Browser
import org.openqa.selenium.WebElement

class VerifyText{
  
  public void run(def params){
  //  WebElement element = Elements.find(params,Browser.Driver)
            String ElementName = (String) params.get("Element Name");
      String PageName = (String) params.get("Page Name"); 
      
      WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
    
    assert element.getText() == params.Text, "Error expected text: ${params.Text} does not match text on the UI: ${element.getText()}"
  }
}