package actions.selenium
import actions.selenium.utils.GetObjectRepository

import actions.selenium.utils.Elements
import actions.selenium.Browser
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor

class RightClick{

  public run(def params){
   // WebElement element = Elements.find(params,Browser.Driver)
      String ElementName = (String) params.get("Element Name");
   String PageName = (String) params.get("Page Name"); 
   WebElement element = GetObjectRepository.find_Element(ElementName,PageName);

    Actions action = new Actions(Browser.Driver)

    action.contextClick(element).perform()

  }
}