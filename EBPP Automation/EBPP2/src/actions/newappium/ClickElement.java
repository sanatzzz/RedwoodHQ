package actions.newappium;
import java.util.HashMap;
import io.appium.java_client.MobileElement;
import actions.utils.GetObjectRepository;
import actions.utils.Elements;

class ClickElement {
	public void run(HashMap<String, String> params) throws Exception {
          System.out.println("Hi run0");
		//MobileElement element = Elements.find(params, Driver.driver);
        String ElementName = (String) params.get("Element Name");
        String PageName = (String) params.get("Page Name"); 
        MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);
        System.out.println("Hi run");
		element.click();
		return;
	}
}
