package actions.selenium.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


class GetOTPLogin{
    public String run(HashMap<String, Object> params){
        
              String otp = "";
              Driver myDriver = new oracle.jdbc.driver.OracleDriver();

              // String URL = null;
              // String USER = null;
              // String PASS =null;
				
        		String URL = "jdbc:oracle:thin:@10.129.66.20:1521:FIDCS";
              	String USER = "FDCMAWPD";
              	String PASS = "fdcmawpd";
    
             System.out.println("URL : "+URL+" User : "+USER+" PASS :  "+PASS );

         Connection conn;
         try {
                conn = DriverManager.getConnection(URL, USER, PASS);
                Statement stmt = null;
              //  String Mobile_No =  (String)params.get("Mobile_No");
                             
               // String SQL = " SELECT TRANSACTIONTYPE FROm TIBCOCLE.LOG TBL Where LOGID = 156414286";
                 
                String SQL = " SELECT OTP FROM otp_live WHERE otp_live.field_values LIKE '%918551908079% " +
                    " AND OTP_EXPIRY_DATETIME like SYSDATE   order by R_MOD_TIME, OTP " ;
                stmt = conn.createStatement();
                System.out.println("Debug point 1");
                ResultSet rs = stmt.executeQuery(SQL);
                System.out.println("Executed");
                
                
                while(rs.next())
                {
                    otp = rs.getString(1);
                    System.out.println(otp);
                }
              } 
    	catch (SQLException e) 
         		{
                // TODO Auto-generated catch block
                e.printStackTrace();
         		}
        		System.out.println(otp);
              return otp;
       }

    

        
    }
