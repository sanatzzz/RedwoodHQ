package TestNG.pageObjects;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import TestNG.test.Capabilities;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.android.AndroidDriver;
import java.util.concurrent.TimeUnit;

public class Home {

    AppiumDriver<MobileElement> driver;

    @AndroidFindBy(id="btnPayments")

    MobileElement payments;
    
  
    public Home(AppiumDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements
        System.out.println("Creating Elements");

        PageFactory.initElements(new AppiumFieldDecorator(driver,5,TimeUnit.SECONDS), this);

    }

    //Set user name in textbox

    public void clickpayment(){
        

        payments.click();

       
    }

    /**

     * This POM method will be exposed in test case to login in the application

     */

    public void clickpayments(){

        //Click on payments

        this.clickpayment();
                
    }

}