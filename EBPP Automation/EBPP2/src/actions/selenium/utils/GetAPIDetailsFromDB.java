package actions.selenium.utils;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class GetAPIDetailsFromDB{

    /*
    	This function will return API details if database connection is successful
        Else it will return stacktrace.
        Input Parameters : test case name
        Output Parameters : API Details in Hashmap format. Below is an Example.
        					key				| 	value
                            url				|	xyz.co.in/getabc
                            method_type		|	get
                            request_body	|	{"req":"abc"}
                            response_body	|	{"res":"pqr"}
                            error			|	nullpointerException at line23....
    
    */
    
    public Map <String,String> getDetails(String test_case_name){
        	
        /******** Declaration of variables ***********/
        	Connection con;
        	PreparedStatement stmt;
		    ResultSet rs;
		    String url="";
		    String method_type="";
		    String inputObject="";
		    String expectedOutput="";
        	Map <String,String> apiDetails= new HashMap<String,String>();
        
        /********** Connecting to Database and fetching details **************/
        
        try{
			   Class.forName("com.mysql.jdbc.Driver");
		       con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testapi","root","root");
		       stmt=con.prepareStatement("SELECT * FROM `api_test_data_main` WHERE test_case_name='"+test_case_name+"'");
		       rs = stmt.executeQuery();
		       while(rs.next()){
		            url = rs.getString("url");
		            method_type = rs.getString("method_type");
		            inputObject = rs.getString("request_body");
		            expectedOutput = rs.getString("response_body");
               }
                rs.close();
                stmt.close();
                apiDetails.put("url",url);
                apiDetails.put("method_type",method_type);
                apiDetails.put("request_body",inputObject);
                apiDetails.put("response_body",expectedOutput);
        
    }
        catch(Exception e){
            
                System.out.println("Database Operation failed. Test API details could not be fetched.");                
            	apiDetails.put("ErrorTrace",e.getStackTrace().toString());
        }
        
       return apiDetails;
}
    
}