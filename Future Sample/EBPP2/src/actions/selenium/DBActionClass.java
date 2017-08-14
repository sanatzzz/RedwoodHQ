package actions.selenium;

import java.util.*;
import actions.selenium.utils.DBActionHandler;
import org.junit.Assert;

public class DBActionClass{
    
    public void run(HashMap<String, Object> params){
        System.out.println("fetching query....");
        String test_case_name = params.get("test_case_name").toString();
        System.out.println("fetching query....");
        
       DBActionHandler dh1= new  DBActionHandler();
        
       String que = dh1.get_Sql_Query(test_case_name);
    LinkedList<String> dataFromOurDB = new LinkedList<String>();
    LinkedList<String> dataFromServer = new LinkedList<String>();    
    
         dataFromOurDB = dh1.DataFromMySql(que);
         dataFromServer = dh1.DataFromOracle(que);
        
       System.out.println("Data From DB"+dataFromOurDB);
          System.out.println("Data From Server"+dataFromServer);
        //int l =dataFromOurDB.size();
        int a=0;
        for(int p=0;p<dataFromOurDB.size();p++)
        {
            String temp = (String) dataFromOurDB.get(p);
            String temp1 = (String) dataFromServer.get(p);
            if((temp !=null && temp1 == null) || (temp ==null && temp1 != null) ){
                System.out.println("both data are different");
            break;}
            else if(temp!=null && temp!=null){
            	if(temp.equals(temp1)){a++;
                  continue;}
            	else{
            		System.out.println("both data are different");
            		break;
            	}
            	}
            else if(temp==null && temp1==null){
            	a++;
            	continue;
            }
            		
            		
            
                
            }
        if(a==dataFromOurDB.size()){
            
            
            System.out.println("both data matches  each other");
        }
        else {
            System.out.println("match is a fail");
        }
        
        
        /*if(dataFromOurDB.containsAll(dataFromServer))
        {
          System.out.println("both data matches  each other");  
        }*/
        
    }
}