package actions.selenium.utils;

import java.util.*;

class ConcatenateStringsInArray{
    public String run(HashMap<String, Object> params){
     String abc="";
        String returnValue="";  

        ArrayList<String> inputArray = (ArrayList<String>)params.get("Strings to be Concatenated");
        String separator = params.get("separator").toString(); 
        if(separator==null){
            separator="";
        }
        if(separator.equals("space")){
            separator=" ";
        }
        for (String input : inputArray){
            System.out.println("array element : "+input);
            if(returnValue == ""){
                returnValue =returnValue + input ;
            }
            else{
                returnValue = returnValue + separator + input;
            }
            
        }
        System.out.println("returning: "+ returnValue);
        return returnValue;
    }
}