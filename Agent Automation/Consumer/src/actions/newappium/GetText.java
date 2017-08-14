package actions.newappium;

/**
 * Created by dinkark on 03-Oct-2016
 */
import java.util.HashMap;
import actions.selenium.utils.GetObjectRepository;
import io.appium.java_client.MobileElement;
import utils.Elements;

class GetText {
	public String run(HashMap<String, String> params) throws Exception {
		//MobileElement element = Elements.find(params, Driver.driver);
                    String ElementName = (String) params.get("Element Name");
            String PageName = (String) params.get("Page Name"); 
            MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);
		return element.getText();
	}
}