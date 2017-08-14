package actions.newappium;
import java.util.HashMap;
import io.appium.java_client.MobileElement;
import utils.Elements;
import java.util.*;
import actions.selenium.utils.GetObjectRepository;
import org.junit.Assert;
import utils.Constants;

/**
 * Created by dinkark on 13-Oct-2016
 */
class CheckText {
	public void run(HashMap<String, String> params) throws Exception {
        MobileElement element= null;
		//MobileElement element = Elements.find(params, Driver.driver);
        String ElementName = (String) params.get("Element Name");
        String PageName = (String) params.get("Page Name"); 

        element = GetObjectRepository.find_Element(ElementName,PageName); 
		Assert.assertEquals(params.get(Constants.TEXT), element.getText());
	}
}
