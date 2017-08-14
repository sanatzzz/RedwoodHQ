package actions.selenium;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;

class GetOtpLogin{
   public String run(HashMap<String, Object> params) {
              String otp = null;
              String forgot_OTP = null;
              Driver myDriver = new oracle.jdbc.driver.OracleDriver();
             

               String URL = "jdbc:oracle:thin:@10.135.25.214:1521:sitorcl";
               String USER = "rouser";
               String PASS = "Qwcbd5#m";


                    
              System.out.println("URL " + URL + " User " + USER + " PASS " + PASS);

              Connection conn;
              try {
                     conn = DriverManager.getConnection(URL, USER, PASS);
                     Statement stmt = null;
                     // String SQL =
                     // "select USER_MOBILE, DATETIME, OTP from JIO_IAM.otp_dtl where USER_MOBILE like '%"+objectSetVal1+"%' order by DATETIME desc ";
                 String MobileNumber =(String)params.get("Mobile Number");
                String SQL = "SELECT transaction, time_stamp  FROM TIBCOCLE.LOG WHERE COMPONENTNAME = 'NotificationService' AND transactiontype = 'Received' AND message = 'Request has been received successfully'AND transaction like '%"+MobileNumber+"%' AND TIME_STAMP > SYSDATE - 1/24 ORDER BY TIME_STAMP desc";
 

                     System.out.println("executed query");
                     stmt = conn.createStatement();
                     System.out.println("Illenu illa");
                     ResultSet rs = stmt.executeQuery(SQL);

                     System.out.println("Executed");
                     rs.next();

                     System.out.println("OTP is " + rs.getInt(3));
                     forgot_OTP = Integer.toString(rs.getInt(3));
                     System.out.println("String OTP is " + forgot_OTP);
                     int otp_flag = 1;
                     if (forgot_OTP.length() == 5) {
                           forgot_OTP = "0" + forgot_OTP;
                           // break;
                     }
                     //

                     otp = forgot_OTP;

                     rs.close();
                     conn.close();

              } catch (SQLException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
              }

              return otp;
       }

}