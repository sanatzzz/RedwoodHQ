package actions.newappium;

/**
 * Created by dinkark on 05-Oct-2016
 */
import java.util.HashMap;
import actions.selenium.utils.GetObjectRepository;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import utils.Elements;

class LongPress {
	public void run(HashMap<String, String> params) throws Exception {
		TouchAction action = new TouchAction(Driver.driver);
		//MobileElement element = Elements.find(params, Driver.driver);
        String ElementName = (String) params.get("Element Name");
        String PageName = (String) params.get("Page Name"); 
        MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);
		action.longPress(element).perform();
	}
}