package actions.selenium.utils;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import java.sql.Timestamp;

public class DatabaseHandler{
    
     /*
    	This function will return Expected Values details if database connection is successful
        
        Input Parameters : test case name
        Output Parameters : Expected Values in Hashmap format. Below is an Example.
        					key				| 	value
                            param1			|	value1
                            param2			|	value2
                            
    */
    
    public  Map <String,String> getExpectedParams(String test_case_name){
        
      /******** Declaration of variables ***********/
        	Connection con;
        	PreparedStatement stmt;
		    ResultSet rs;
		    
        	Map <String,String> expectedValues= new HashMap<String,String>();
        
        /********** Connecting to Database and fetching details **************/
        
        try{
			Class.forName("com.mysql.jdbc.Driver");
		    con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");  
        	stmt=con.prepareStatement("select * from expected_param_values WHERE test_case_name='"+test_case_name+"'");
			rs = stmt.executeQuery();
		    	while(rs.next()){
                    
		        	expectedValues.put(rs.getString("param_name"), rs.getString("value"));
		       	}
            rs.close();
		    con.close();
		    stmt.close();
        }
        catch(Exception e){
            
                System.out.println("Database Operation failed. Parameter details could not be fetched.");                
            	//expectedValues.put("ErrorTrace",e.getStackTrace().toString());
        }
        
       return expectedValues;
    
    }
    
     /*
    	This function will return API details if database connection is successful
        
        Input Parameters : test case name,environment
        Output Parameters : API Details in Hashmap format. Below is an Example.
        					key					| 	value
                            endpoint_url		|	xyz.co.in/getabc
                            method_type			|	get
                            json_request		|	{"req":"abc"}
                            expected_response	|	{"res":"pqr"}
                            wsdl_path			|	C:\Users\sanat.shetty\Desktop\DemoRESTAssured\Resources
                            webservice_type		|	REST/SOAP
                            header_key			|	TIBCO_getPanDetails
    
    */
    
    public Map <String,String> getAPIDetails(String test_case_name, String environment){
        	
        /******** Declaration of variables ***********/
        	Connection con;
        	PreparedStatement stmt;
		    ResultSet rs;
		    String url="";
		    String method_type="";
		    String jsonRequest="";
		    String expectedResponse="";
        	String wsdlPath="";
        	String webserviceType="";
        	String headerKey="";
        	String componentName ="";
        	String endpointUrl="";
        	Map <String,String> apiDetails= new HashMap<String,String>();
        
        /********** Connecting to Database and fetching details **************/
        
        try{
			System.out.println("Trying to access database...");     
            Class.forName("com.mysql.jdbc.Driver");
		       con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");
		       stmt=con.prepareStatement("SELECT * FROM `api_test_data_main` WHERE test_case_name='"+test_case_name+"'");
		       rs = stmt.executeQuery();
             System.out.println("Database Accessed....");         
		       while(rs.next()){
		            url = rs.getString("endpoint_url");
		            method_type = rs.getString("method_type");
		            jsonRequest = rs.getString("json_request");
		            expectedResponse = rs.getString("expected_response");
                   	wsdlPath = rs.getString("wsdl_path");
                   	webserviceType = rs.getString("webservice_type");
                   	headerKey = rs.getString("header_key");
                   	componentName = rs.getString("component_name");
                }
               
		    	System.out.println("componentName :" +componentName);   
            	System.out.println("environment :" +environment);   
            	stmt=con.prepareStatement("SELECT * FROM `comp_base_url` WHERE component_name='"+componentName+"' and environment = '"+environment+"'");
            	rs = stmt.executeQuery();	
            	System.out.println("statement :" +stmt.toString());   
            	while(rs.next()){
		            endpointUrl = rs.getString("base_url")+url;
                     System.out.println("URL :" +endpointUrl);                
		        }
            	rs.close();
		    	stmt.close();
            	con.close();
            
                apiDetails.put("endpointURL",endpointUrl);
                apiDetails.put("method_type",method_type);
                apiDetails.put("jsonRequest",jsonRequest);
                apiDetails.put("expectedResponse",expectedResponse);
            	apiDetails.put("wsdlPath",wsdlPath);
            	apiDetails.put("webserviceType",webserviceType);
            	apiDetails.put("headerKey",headerKey);
        
    }
        catch(Exception e){
            
                System.out.println("Database Operation failed. Test API details could not be fetched.");                
            	apiDetails.put("ErrorTrace",e.getStackTrace().toString());
        }
        
       return apiDetails;
}
     
    
    /*
    	This function will return header details if database connection is successful
        
        Input Parameters : header_key,add_header_val
        Output Parameters : API Details in Hashmap format. Below is an Example.
        					key			| 	value
                            header1		|	value1
                            header2		|	value2
                            add_header_tag
                            add_header_val
    
    */
     public Headers getHeaders(String header_key,String add_header_tag, String add_header_val){
        	
        /******** Declaration of variables ***********/
        	Connection con;
        	PreparedStatement stmt;
		    ResultSet rs;
		    List<Header> headerList = new LinkedList<Header>();
        	String headerKey="";
        	Map <String,String> apiDetails= new HashMap<String,String>();
        
        /********** Connecting to Database and fetching header details **************/
        
        try{
			   	Class.forName("com.mysql.jdbc.Driver");
		       	con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");
		       	stmt=con.prepareStatement("SELECT * FROM `headers` WHERE header_key='"+header_key+"'");
		       	rs = stmt.executeQuery();
		    	
            	String[] add_header_tag_arr = new String[5];
            	String[] add_header_val_arr = new String[5];
               
            	if(add_header_tag.contains("|") && add_header_val.contains("|")){
                    add_header_tag_arr = add_header_tag.split("\\|");
                    add_header_val_arr = add_header_val.split("\\|");
                    for (int i=0; i<add_header_tag_arr.length;i++){
                        headerList.add(new Header(add_header_tag_arr[i],add_header_val_arr[i]));
                        System.out.println("ADD header tag: "+add_header_tag_arr[i]);
                        System.out.println("ADD header value: "+add_header_val_arr[i]);
                    }
                } else if(!(add_header_val.isEmpty())){
                    headerList.add(new Header(add_header_tag,add_header_val));
                    System.out.println("header tag: "+add_header_tag);
                    System.out.println("header value: "+add_header_val);
                    
                }
		       	while (rs.next()) {
					Header header11 = new Header(rs.getString("header_name"), rs.getString("header_value"));
		            headerList.add(header11);
				}
				System.out.println("total headers : "+headerList.size());
            	rs.close();
		    	con.close();
		    	stmt.close();
        
    }
        catch(Exception e){
            
                System.out.println("Database Operation failed.Header details could not be fetched.");                
            	
        }
       Headers headers = new Headers(headerList);
       return headers;
}
    
    
     /*
    	This function will insert webservice request and response details to log table
        Input Parameters : test_case_name,request,response,endpointURL
        
      */
    
    public void insertDataToAPISampleResponse(String test_case_name,String request,String response,String endpointURL){
         /******** Declaration of variables ***********/
       		Connection con;
        	PreparedStatement stmt;
        
        
        
        /**************Adding data to Log table ******************/
        	try{
        		Class.forName("com.mysql.jdbc.Driver");
		       	con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");
                Timestamp timestamp = new Timestamp(new Date().getTime()); 
    			String query = " insert into api_sample_response (test_case_name,endpoint_URL,request,response)"+ " values (?, ?, ?, ?)";

    	      
    	      	PreparedStatement preparedStmt = con.prepareStatement(query);
    	      	preparedStmt.setString (1,test_case_name);
    	     	preparedStmt.setString (2,endpointURL);
                preparedStmt.setString (3,request);
                preparedStmt.setString (4, response);
    	      	        
    	      	preparedStmt.execute();
                System.out.println("Data inserted to Sample Table");
                con.close();
		    	preparedStmt.close();
            }
        catch(Exception e){
            System.out.println("Can't insert data to SampleResponse.");
            e.printStackTrace();
        }
    }
    


      
      
      public void insertDataToLogTable(String test_case_name,String request,String response,String endpointURL){
        
          /******** Declaration of variables ***********/
        	Connection con;
        	PreparedStatement stmt;
      
        
        
        /**************Adding data to Log table ******************/
        	 try{
        		Class.forName("com.mysql.jdbc.Driver");
		       	con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");
                Timestamp timestamp = new Timestamp(new Date().getTime()); 
    			String query = " insert into test_logs (test_case_name,endpoint_url, request, response, timestamp)"+ " values (?, ?, ?, ?, ?)";

    	      
    	      	PreparedStatement preparedStmt = con.prepareStatement(query);
    	      	preparedStmt.setString (1,test_case_name);
    	     	preparedStmt.setString (2,endpointURL);
    	      	preparedStmt.setString   (3, request);
                preparedStmt.setString   (4, response);
    	      	preparedStmt.setTimestamp(5, timestamp);
        
    	      	preparedStmt.execute();
                
                con.close();
		    	preparedStmt.close();
            }
        catch(Exception e){
            System.out.println("Can't insert data to Logs.");
            e.printStackTrace();
        }
    }
    
       /*
    	This function will return checksum metadata if database connection is successful
        
        Input Parameters : api  name
        Output Parameters : Checksum tags and values. Below is an Example.
        					
                            
    */
    
    public Map<String,String> getChecksumData(String api_name){
        
      /******** Declaration of variables ***********/
        	Connection con;
        	PreparedStatement stmt;
		    ResultSet rs;
		    
        	Map<String,String> checksumData = new LinkedHashMap<String,String>();
        
        /********** Connecting to Database and fetching details **************/
        
        try{
			Class.forName("com.mysql.jdbc.Driver");
		    con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");  
        	stmt=con.prepareStatement("select * from checksum_data WHERE api_name='"+api_name+"'");
			rs = stmt.executeQuery();
            String[] tagNames= new String[50];
            String[] tagValues= new String[50];
		    /*	while(rs.next()){
                    
		        	checksumData.put(rs.getString("tag_names"), rs.getString("tag_values"));
		       	} */
            
            while(rs.next()){
            	tagNames = rs.getString("tag_names").split("\\|");
                tagValues = rs.getString("tag_values").split("\\|");
            }
            
            for(int i=0;i<tagNames.length;i++){
               
                if( tagNames[i] != null && !tagNames[i].isEmpty()){
                    checksumData.put(tagNames[i],tagValues[i]); 
                } else {
                   	 checksumData.put("nullstring_"+i,"nullstring_"+i);
                }
                
                //System.out.println("CheckString " + i + " :"+ tagNames[i] + " " + tagValues[i]);
            }
            
             //System.out.println(checksumData);
            
            rs.close();
		    con.close();
		    stmt.close();
        }
        catch(Exception e){
            
                System.out.println("Database Operation failed. checksum details could not be fetched.");                
            	//expectedValues.put("ErrorTrace",e.getStackTrace().toString());
        }
        
       return checksumData;
    
    }
    
}
	
   