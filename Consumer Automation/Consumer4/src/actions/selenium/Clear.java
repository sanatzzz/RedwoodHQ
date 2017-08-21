package actions.selenium;
import actions.selenium.utils.GetObjectRepository;
import java.util.*;
import org.openqa.selenium.WebElement;

class Clear{
    public void run(HashMap<String, Object> params) throws Exception{
        
              String ElementName = (String) params.get("Element Name");
      String PageName = (String) params.get("Page Name"); 
      
      WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
          element.clear();
    }
}