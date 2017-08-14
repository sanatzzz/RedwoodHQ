package actions.selenium;

import java.util.*;

class SimplePrint{
    public void run(HashMap<String, Object> params){
        
        ArrayList <String> tags =  (ArrayList<String>)params.get("Tag Values");
        int i=0;
        for (String tag : tags){
            System.out.println("Tag "+i+":"+tag);
            i++;
        }
        
    }
}