package actions.selenium;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.util.*;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import actions.selenium.Browser;
import actions.selenium.utils.GetObjectRepository;



class Clicktable{
    public void run(HashMap<String, Object> params){
        try {
         String ElementName = (String) params.get("Element Name");
         String PageName = (String) params.get("Page Name"); 
         String checkvalue = (String) params.get("Text");
         WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
        List<WebElement> row_table= element.findElements(By.tagName("tr"));
         int row_count = row_table.size();
         System.out.println(row_table.isEmpty());
         int flag11 = 0;
                                for (int row = 0; row < row_count; row++) {
                        //row count of table
                                                                                                System.out.println("inside row");
                                                                                                List<WebElement> Columns_row = row_table.get(row).findElements(By.tagName("td"));
                                                                                                                                
                                                                                                int columns_count = Columns_row.size();
                                                                                                System.out.println("colcount " + columns_count);
                                                                                                for (int column = 0; column < columns_count; column++) {
                            // column count of table
                                                                                                                
                                                                                                                String celText = Columns_row.get(column).getText();
                                                                                                                System.out.println("celtext " + celText);
                            System.out.println("chcekvalue " + checkvalue);
                          //  String checkvalue=params.get("Text").toString();
                                                                                                                if (celText.equalsIgnoreCase(checkvalue))
                            {
                                System.out.println("found");
                                //if input value is equals to element value
                                                                                                                                String actval = celText;
                                                                                                                                Columns_row.get(column).click();
                                                                                                                                System.out.println("actval" + actval);
                                                                                                                                flag11 = 1;
                                                                                                                }

                                                                                                                if (flag11 == 1)
                                                                                                                                break;
                                                                                                }
                                                                                                if (flag11 == 1)
                                                                                                                break;
                                                                                }
           
        }//end of try
         catch(Exception e){
                System.out.println(e);
            }
}
}

