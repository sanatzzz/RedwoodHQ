package actions.selenium.utils;
import java.util.*;

public class ObjectRepository{
    HashMap<String, String>  obr= new HashMap<String, String>();
        public ObjectRepository()
    {
    System.out.println("Inside ObjectRepository");
   // obr.put("Merchant_MobileNumber","r1:0:s1:ot13::content");
     obr.put("Merchant_MobileNumber","r1:0:s1:ot13::content");       
    obr.put("Merchant_Password","//input[@id='r1:0:s1:ot2::content']");
            obr.put("Merchant_Email",".//*[@id='d_pt:milVew:1:it1::content']");
            obr.put("Merchant_Web",".//*[@id='d_pt:milVew:1:it2::content']");
            obr.put("Merchant_PasswordReenter","d_pt:milVew:1:s4:it3::content");
            obr.put("native_mobno","mobileNumber");
            obr.put("native_pwd","password");
            obr.put("native_midselect","");
    obr.put("Con_Wallet_MobileNumber","etMobileNumber");
    obr.put("Con_Wallet_Password","//input[@id='r1:0:s1:ot2::content']");
    }
  
}