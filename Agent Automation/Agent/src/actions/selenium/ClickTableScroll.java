package actions.selenium;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.util.*;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import actions.selenium.Browser;
import actions.selenium.utils.GetObjectRepository;
import org.openqa.selenium.JavascriptExecutor;

class ClickTableScroll{
   public void run(HashMap<String, Object> params) throws Exception{
        
     //WebElement mytable1121 = elem;
       
       String ElementName = (String) params.get("Element Name");
         String PageName = (String) params.get("Page Name"); 
         String checkvalue = (String) params.get("Text");
         WebElement element = GetObjectRepository.find_Element(ElementName,PageName);
                                  // if (elem==null)
                                  // System.out.println("element not found");

                                  List<WebElement> rows_table1121 = element.findElements(By.tagName("tr"));
                                                

                                  // String [] rows_table = new String [50];
                                  // rows_table = mytable.findElements(By.tagName("tr"));
                                  int rows_count1121 = rows_table1121.size();
                                  System.out.println(rows_table1121.isEmpty());
                                  System.out.println("rowcount " + rows_count1121);
                                  int flag1121 = 0;
                                  for (int row = 0; row < rows_count1121; row++) {
                                         System.out.println("inside row");
                                         WebElement td = rows_table1121.get(row).findElement(By.tagName("td"));
                                                      
                                        ((JavascriptExecutor)Browser.Driver).executeScript("arguments[0].scrollIntoView();", td);
                                                       
                                         td.click();
                                         WebElement Columns_row = rows_table1121.get(row).findElement(By.className("af_selectBooleanRadio_item-text"));
                                                       
                                                       
                                                                     
                                         System.out.println("Cell text is "+ Columns_row.getText());
                                                       
                                         if (Columns_row.getText().equals(checkvalue)) {
                                                System.out.println("Clicked");
                                                flag1121 = 1;
                                                Columns_row.click();
                                                break;
                                         }
                                  }
                                  if (flag1121 == 1)
                                         //Update_Report("executed");
                                       System.out.println("Executed");
   }
}