package actions.selenium;
import actions.selenium.utils.GetObjectRepository;
import actions.selenium.utils.Elements;
import actions.selenium.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

class Select1{
    public void run(HashMap<String, Object> params) throws Exception{
          String ElementName = (String) params.get("Element Name");
      String PageName = (String) params.get("Page Name"); 
        String VisibleText = (String) params.get("Visible Text"); 
         WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
 new Select(element).selectByVisibleText(VisibleText);
        
        
    }
}