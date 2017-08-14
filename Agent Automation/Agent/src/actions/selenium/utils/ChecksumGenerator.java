package actions.selenium.utils;

import actions.selenium.utils.DatabaseHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import actions.selenium.Global_Params;
import com.fis.encrypt.EncryptionUtil;

import java.util.*;

class ChecksumGenerator{
    public String run(HashMap<String, Object> params){
        
        String [] replaceTags = (params.get("Tags To Be Replaced").toString()).split("\\|");
        String [] replaceValues = (params.get("New Values For Tags").toString()).split("\\|");
        String seed = params.get("Seed").toString();
        String api_name = params.get("Api Name").toString();
        Map<String,String> checksumData = new DatabaseHandler().getChecksumData(api_name);
        Map<String,String> updatedChecksumData =replaceTagValues(replaceTags,replaceValues,checksumData,api_name);
        String checkSumString = "";
        String[] checknullString = new String[10];
        
        for(String key:checksumData.keySet()){
            checknullString = key.toString().split("_");
            if(checknullString[0].equals("nullstring")){
                checkSumString = checkSumString + "|";
            } 
            else if(checkSumString != ""){
                checkSumString = checkSumString + "|" + updatedChecksumData.get(key);   
                
            } 
            else {
                checkSumString = updatedChecksumData.get(key);
            }
            
        }
        EncryptionUtil ec = new EncryptionUtil();
		String checkSum = ec.hmacDigest(checkSumString, seed, "HMACSHA256");
		
     	System.out.println("Data : " + checkSumString);
		//System.out.println("Date : " +strDate );
		System.out.println("Checksum value : " + checkSum);
		
        return checkSum;
		
    }
    
    public Map<String,String> replaceTagValues(String[] tagName, String[] values, Map<String,String> ChecksumData,String api_name){
        
        for (int i=0;i<tagName.length;i++){
            if(values[i].equals("CURR_DATE")){
                Global_Params.generateChecksumTimestamp(api_name);
                ChecksumData.put(tagName[i],Global_Params.getChecksumTimestamp(api_name));
            }
            else {
                ChecksumData.put(tagName[i],values[i]);
            }
        }
        return ChecksumData;
    }
    
    
    
}