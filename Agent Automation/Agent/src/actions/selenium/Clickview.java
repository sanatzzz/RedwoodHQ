package actions.selenium;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.util.*;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import actions.selenium.Browser;
import actions.selenium.utils.GetObjectRepository;

class Clickview{
    public void run(HashMap<String, Object> params){
        try{
        String ElementName = (String) params.get("Element Name");
         String PageName = (String) params.get("Page Name"); 
         String checkvalue = (String) params.get("Text");
         WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
       List<WebElement> rows_table2 = element.findElements(By.tagName("tr"));
                                                                                                                
                                                                                // String [] rows_table = new String [50];
                                                                                // rows_table = mytable.findElements(By.tagName("tr"));
                                                                                int rows_count2 = rows_table2.size();
                                                                                System.out.println(rows_table2.isEmpty());
                                                                                System.out.println("rowcount " + rows_count2);
                                                                                int flag2 = 0;
                                                                                for (int row = 0; row < rows_count2; row++) {
                                                                                                System.out.println("inside row");
                                                                                                List<WebElement> Columns_row = rows_table2.get(row).findElements(By.tagName("td"));
                                                                                                int columns_count = Columns_row.size();
                                                                                                System.out.println("colcount " + columns_count);
                                                                                                for (int column = 0; column < columns_count; column++) {

                                                                                                                String celText = Columns_row.get(column).getText();
                                                                                                                System.out.println("celtext of view " + celText);
                                                                                                                if (celText.equalsIgnoreCase(checkvalue)) {
                                                                                                                                System.out.println("view Found");
                                                                                                                                // WebElement
                                                                                                                                // View=Columns_row.get(10).findElement(By.tagName("a"));
                                                                                                                                WebElement View = rows_table2.get(row).findElement(By.tagName("a"));
                                                                                                                                System.out.println("view Found");
                                                                                                                                View.click();
                                                                                                                                flag2 = 1;
                                                                                                                }
                                                                                                                if (flag2 == 1)
                                                                                                                                break;
                                                                                                }
                                                                                                if (flag2 == 1)
                                                                                                                break;
                                                                                }
        }
      catch(Exception e){
                System.out.println(e);
            }

                                                                
    }
}



