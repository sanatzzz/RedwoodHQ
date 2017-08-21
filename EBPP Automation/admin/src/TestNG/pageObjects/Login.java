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

public class Login {

    AppiumDriver<MobileElement> driver;

    @AndroidFindBy(id="etMobileNumber")

    MobileElement username;

    
    @FindBy(id="etPassword")

    MobileElement password;
 

    @AndroidFindBy(id="btnSignin")

    MobileElement loginsignup;
    
    @AndroidFindBy(id="btnSignin")

    MobileElement login;
    
    
 

    public Login(AppiumDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements
        System.out.println("Creating Elements");

        PageFactory.initElements(new AppiumFieldDecorator(driver,5,TimeUnit.SECONDS), this);

    }

    //Set user name in textbox

    public void setUserName(String strUserName){
        

        username.sendKeys(strUserName);
        driver.navigate().back();

       
    }

    

    //Set password in password textbox

    public void setPassword(String strPassword){

    password.sendKeys(strPassword);
         driver.navigate().back();

    }

    

    //Click on loginsignup button

    public void clickLoginsignup(){

            loginsignup.click();

    }
    
        public void clickLogin(){

            login.click();

    }
    


    /**

     * This POM method will be exposed in test case to login in the application

     * @param strUserName

     * @param strPasword

     * @return

     */

    public void loginToConsumer(String strUserName,String strPassword){

        //Fill user name

        this.setUserName(strUserName);
        
        
        //Click Login button
        

        this.clickLoginsignup();

        //Fill password

        this.setPassword(strPassword);

        this.clickLogin();
                

    }

}