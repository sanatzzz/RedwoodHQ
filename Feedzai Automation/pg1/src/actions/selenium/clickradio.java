package actions.selenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.util.*;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import actions.selenium.Browser;
import actions.selenium.utils.GetObjectRepository;

class clickradio {
    public void run(HashMap<String, Object> params) {
       try{ 
         String ElementName = (String) params.get("Element Name");
         String PageName = (String) params.get("Page Name"); 
         WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
					List<WebElement> divsindiv = element.findElements(By
							.tagName("div"));
        System.out.println("divsindiv " + divsindiv);
					// String [] rows_table = new String [50];
					// rows_table = mytable.findElements(By.tagName("tr"));
					int divcount = divsindiv.size();
					System.out.println(divsindiv.isEmpty());
					System.out.println("divcount " + divcount);
					for (int row = 0; row < divcount; row++) {
						System.out.println("inside div");
						String Columns_row = divsindiv.get(row).getText();
						if (Columns_row.contains(params.get("Text").toString())) {
							//flag4 = 1;
                         System.out.println("String " + Columns_row.contains(params.get("Text").toString()));
						WebElement Div = divsindiv.get(row).findElement(By.className("af_selectBooleanRadio_native-input"));
                           
                        Div.click();
                           System.out.println("complete" );
						}}}
        catch(Exception e){
            System.out.println(e);
        }
    
    }}