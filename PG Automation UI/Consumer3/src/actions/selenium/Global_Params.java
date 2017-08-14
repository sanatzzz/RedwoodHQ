package actions.selenium;

import java.text.SimpleDateFormat;
import java.util.*;

public class Global_Params{
    public static Map <String,String> checksum_timestamp = new HashMap <String,String>();

    public static void generateChecksumTimestamp(String api_name){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        checksum_timestamp.put(api_name,strDate);
    }
    public static String getChecksumTimestamp(String api_name){
       return checksum_timestamp.get(api_name);
    } 
}