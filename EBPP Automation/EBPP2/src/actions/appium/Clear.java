package actions.appium;
import java.util.HashMap;
import io.appium.java_client.MobileElement;
import utils.Elements;
import java.util.*;
import actions.selenium.utils.GetObjectRepository;

class Clear{
    public void run(HashMap<String, String> params) throws Exception{
        	MobileElement element = Elements.find(params, Driver.driver);
           // String ElementName = (String) params.get("Element Name");
           // String PageName = (String) params.get("Page Name"); 
           // MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);
	        element.clear();
            return;
    }
}