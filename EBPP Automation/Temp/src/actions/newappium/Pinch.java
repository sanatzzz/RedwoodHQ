package actions.newappium;
import java.util.HashMap;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import actions.selenium.utils.GetObjectRepository;
import actions.utils.Elements;

class Pinch {
	public void run(HashMap<String, String> params) throws Exception {
		MultiTouchAction multiTouch = new MultiTouchAction(Driver.driver);
		//MobileElement element = Elements.find(params, Driver.driver);
        String ElementName = (String) params.get("Element Name");
        String PageName = (String) params.get("Page Name"); 
        MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);
		int y = element.getCenter().getY();
		int x = element.getCenter().getX();
		int scrHeight = Driver.driver.manage().window().getSize().getHeight();
		int yOffset = 100;

		if (y - 100 < 0) {
			yOffset = y;
		} else if (y + 100 > scrHeight) {
			yOffset = scrHeight - y;
		}

		TouchAction action0 = new TouchAction(Driver.driver).press(x, y - yOffset).moveTo(0, yOffset).release();
		TouchAction action1 = new TouchAction(Driver.driver).press(x, y + yOffset).moveTo(0, -yOffset).release();

		multiTouch.add(action0).add(action1);

		multiTouch.perform();
	}
}
