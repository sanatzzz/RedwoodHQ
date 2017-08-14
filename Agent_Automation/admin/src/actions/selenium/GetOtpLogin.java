package actions.selenium;

import java.util.*;

class GetOtpLogin{
    private String Get_Otp1(String objectSetVal1) {
              String otp = null;
              String forgot_OTP = null;
              Driver myDriver = new oracle.jdbc.driver.OracleDriver();
              String URL = null;
              String USER = null;
              String PASS = null;

               String URL = "jdbc:oracle:thin:@10.135.25.214:1521:sitorcl";
               String USER = "rouser";
               String PASS = "Qwcbd5#m";
              for (int column10 = 0; column10 < DTsheet.getColumns(); column10++) {

                    
              System.out.println("URL " + URL + " User " + USER + " PASS " + PASS);

              Connection conn;
              try {
                     conn = DriverManager.getConnection(URL, USER, PASS);
                     Statement stmt = null;
                     // String SQL =
                     // "select USER_MOBILE, DATETIME, OTP from JIO_IAM.otp_dtl where USER_MOBILE like '%"+objectSetVal1+"%' order by DATETIME desc ";
                  MobileNumber =(String)params.get("Mobile Number");
                     String SQL = "select FIELD_VALUES,R_CRE_TIME,OTP  from otp_live  where FIELD_VALUES like '%"
                                  + MobileNumber + "%' order by R_CRE_TIME desc";
                     System.out.println("executed query");
                     stmt = conn.createStatement();
                     System.out.println("Illenu illa");
                     ResultSet rs = stmt.executeQuery(SQL);

                     System.out.println("Executed");
                     rs.next();

                     System.out.println("OTP is " + rs.getInt(3));
                     forgot_OTP = Integer.toString(rs.getInt(3));
                     System.out.println("String OTP is " + forgot_OTP);
                     otp_flag = 1;
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