package actions.newappium;

/**
 * Created by dinkark on 05-Oct-2016
 */
import java.util.HashMap;
import actions.utils.GetObjectRepository;
import io.appium.java_client.MobileElement;
import utils.Elements;

class SetText {

	public void run(HashMap<String, String> params)throws Exception {
		//MobileElement element = Elements.find(params, Driver.driver);
          String ElementName = (String) params.get("Element Name");
          String PageName = (String) params.get("Page Name"); 
          MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);
        
		try {
			Driver.driver.hideKeyboard();
			element.click();
		} catch (Exception e) {
			element.click();
		}
		element.sendKeys(params.get("Text"));

	}
}
