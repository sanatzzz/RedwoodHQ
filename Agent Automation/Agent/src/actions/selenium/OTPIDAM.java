package actions.selenium;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;

class OTPIDAM{
   public String run(HashMap<String, Object> params)  {
              String otp = null;
              Driver myDriver = new oracle.jdbc.driver.OracleDriver();

              // String URL = null;
              // String USER = null;
              // String PASS =null;

                        String URL = "jdbc:oracle:thin:@10.135.25.214:1521:sitorcl";
                        String USER = "rouser";
                        String PASS = "Qwcbd5#m";
    
             System.out.println("URL : "+URL+" User : "+USER+" PASS :  "+PASS );

         Connection conn;
         try {
                conn = DriverManager.getConnection(URL, USER, PASS);
                Statement stmt = null;
               String MobileNumber =  (String)params.get("Mobile Number");
                             
                     String SQL = "SELECT transaction, time_stamp  FROM TIBCOCLE.LOG WHERE COMPONENTNAME = 'NotificationService' AND transactiontype = 'Received' AND message = 'Request has been received successfully'AND transaction like '%"
                                  + MobileNumber
                                  + "%' AND TIME_STAMP > SYSDATE - 1/24 ORDER BY TIME_STAMP desc";
                     System.out.println("executed query");
                     stmt = conn.createStatement();
                     System.out.println("Illenu illa");
                     ResultSet rs = stmt.executeQuery(SQL);
                     System.out.println("Executed");
                     while (rs.next()) {
                            Clob clob = rs.getClob(1);
                           if (clob != null) {
                                  String clobStr = clob.getSubString(1, (int) clob.length());
                                  System.out.println(clobStr);
                                  System.out
                                                .println("OTP is "
                                                              + clobStr.indexOf("OTP</Name><Value>")
                                                              + clobStr.substring(
                                                                            clobStr.indexOf("OTP</Name><Value>") + 17,
                                                                            clobStr.indexOf("OTP</Name><Value>") + 17 + 6));
                                  otp = clobStr.substring(
                                                clobStr.indexOf("OTP</Name><Value>") + 17,
                                                clobStr.indexOf("OTP</Name><Value>") + 17 + 6);
                                  System.out.println("OTP " + otp);
                                int   otp_flag = 1;
                                  break;
                           }
                     }
                     //

                     rs.close();
                     conn.close();

              } catch (SQLException e)
           {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }
              return otp;
       }
}