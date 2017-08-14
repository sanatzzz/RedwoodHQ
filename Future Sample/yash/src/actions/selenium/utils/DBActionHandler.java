package actions.selenium.utils;

import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import java.sql.Timestamp;


public class DBActionHandler{
    
    
    public LinkedList<String> DataFromMySql(String Query){
		Connection con1;
		Statement stmt1;
		ResultSet rs1;
		String q1 = Query;
		LinkedList <String> R = new LinkedList<String>();
        int j= 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con1 = DriverManager.getConnection("jdbc:oracle:thin:@10.140.201.159:1521/autompdb","automation","automation@123");
			stmt1 = con1.createStatement();
			rs1 = stmt1.executeQuery(q1);
		
			ResultSetMetaData rsmd1 = rs1.getMetaData();
			int cct1 = rsmd1.getColumnCount();
			
			while(rs1.next()){
				for(int i=1; i<=cct1;i++){
											if(rsmd1.getColumnName(i).equalsIgnoreCase("BILL_DATE")||rsmd1.getColumnName(i).equalsIgnoreCase("SETTLEMENT_FILE_DATE")||rsmd1.getColumnName(i).equalsIgnoreCase("BILL_DUE_DATE")||rsmd1.getColumnName(i).equalsIgnoreCase("BILL_SCHEDULED_DEBIT_DATE")||rsmd1.getColumnName(i).equalsIgnoreCase("BILL_SCHEDULED_BY")||rsmd1.getColumnName(i).equalsIgnoreCase("BILL_SCHEDULED_DATE")||rsmd1.getColumnName(i).equalsIgnoreCase("PAYMENT_DATE")||rsmd1.getColumnName(i).equalsIgnoreCase("RECON_DATE")||rsmd1.getColumnName(i).equalsIgnoreCase("BILL_AMOUNT")||rsmd1.getColumnName(i).equalsIgnoreCase("PAYMENT_AMOUNT")||rsmd1.getColumnName(i).equalsIgnoreCase("CHARGE_AMOUNT")||rsmd1.getColumnName(i).equalsIgnoreCase("DISCOUNT_AMOUNT")||rsmd1.getColumnName(i).equalsIgnoreCase("TAX_AMOUNT")||rsmd1.getColumnName(i).equalsIgnoreCase("DEBIT_AMOUNT")||rsmd1.getColumnName(i).equalsIgnoreCase("AUTHENTICATOR2"))
               								 
               								 {
												j++;
												continue;
			
											}
										else{
												R.add(rs1.getString(i));	
											}	
               							 }
										}
					
			
            System.out.println("data found");
			System.out.println(R);
            System.out.println("Columns Inserted " +R.size());
            System.out.println("Columns Ignored " +j);
			rs1.close();			
			con1.close();
			stmt1.close();
	return R;
		
		} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
		return null;
			
	}
    
    
    
    
    public LinkedList<String> DataFromOracle(String Query){
		Connection con2;
		Statement stmt2;
		ResultSet rs2;
		LinkedList <String> R2 = new LinkedList<String>();
		String q2=Query;
        int k=0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("driver loaded");
			con2 = DriverManager.getConnection("jdbc:oracle:thin:@10.129.66.24:1521:VAYST","APP_EBPP_USER","APP_EBPP_USER");
            System.out.println("connection made");
			stmt2 = con2.createStatement();
            System.out.println("connection statement");
			rs2 = stmt2.executeQuery(q2);
            System.out.println("connection executed");
			ResultSetMetaData rsmd2 = rs2.getMetaData();
			int cct2 = rsmd2.getColumnCount();
			
			while(rs2.next()){
				for(int i=1; i<=cct2;i++){
                    
											if(rsmd2.getColumnName(i).equalsIgnoreCase("BILL_DATE")||rsmd2.getColumnName(i).equalsIgnoreCase("SETTLEMENT_FILE_DATE")||rsmd2.getColumnName(i).equalsIgnoreCase("BILL_DUE_DATE")||rsmd2.getColumnName(i).equalsIgnoreCase("BILL_SCHEDULED_DEBIT_DATE")||rsmd2.getColumnName(i).equalsIgnoreCase("BILL_SCHEDULED_BY")||rsmd2.getColumnName(i).equalsIgnoreCase("BILL_SCHEDULED_DATE")||rsmd2.getColumnName(i).equalsIgnoreCase("PAYMENT_DATE")||rsmd2.getColumnName(i).equalsIgnoreCase("RECON_DATE")||rsmd2.getColumnName(i).equalsIgnoreCase("BILL_AMOUNT")||rsmd2.getColumnName(i).equalsIgnoreCase("PAYMENT_AMOUNT")||rsmd2.getColumnName(i).equalsIgnoreCase("CHARGE_AMOUNT")||rsmd2.getColumnName(i).equalsIgnoreCase("DISCOUNT_AMOUNT")||rsmd2.getColumnName(i).equalsIgnoreCase("TAX_AMOUNT")||rsmd2.getColumnName(i).equalsIgnoreCase("DEBIT_AMOUNT")||rsmd2.getColumnName(i).equalsIgnoreCase("AUTHENTICATOR2"))
                                        		{
													k++;
													continue;
													}
											else{
												R2.add(rs2.getString(i));	
												}		
										}						
							}
            
			System.out.println("Data selected");
			System.out.println(R2);
            System.out.println("Columns Inserted " +R2.size());
            System.out.println("Columns Ignored " +k);
			rs2.close();
			con2.close();
			stmt2.close();
			return R2;	
		
	
		} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
			
	}
	
	
	public String get_Sql_Query(String QueryName)
	{
	    /******** Declaration of variables ***********/
    	Connection con;
    	PreparedStatement stmt;
	    ResultSet rs;
	    String query=null;
        String test_case_name = QueryName;

        try{
			Class.forName("com.mysql.jdbc.Driver");
			
		    con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/demo","root","root");  
		    stmt=con.prepareStatement("select * from query_table1 WHERE QueryName ='"+test_case_name+"'");
		    //stmt=con.prepareStatement("select * from  query_table1 WHERE QueryName='TXN63150421'");
		    rs = stmt.executeQuery();
            System.out.println("point reached");
		    //
		  
		    while(rs.next()){
			String s=rs.getString("QueryName");
			 query=rs.getString("SQL_Query");
			System.out.println(s+"   "+query);
			
			System.out.println("sql query "+query);
		    }
			rs.close();
			con.close();
			stmt.close();
			return query;
        }//try
        catch(Exception e){
            
            System.out.println("Database Operation failed. Parameter details could not be fetched.");                
        	//expectedValues.put("ErrorTrace",e.getStackTrace().toString());
    }//catch
        finally{
        	//System.out.println("hiiii");
        	return query;
        }
	    
//	return query;    
	}// get_Sql_Query(String TestCaseNa
	
	
	
	
    
    
}