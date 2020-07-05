package Utility.internal;

import java.sql.*;
import java.util.HashMap;

public class DatabaseUtil {
    private static Connection connection = null;
    private static final String url = "jdbc:mysql://localhost/payroll_db?serverTimezone=UTC";
    private static final String userName = "root";
    private static final String password = "";
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    private static HashMap encryptKey;

    public DatabaseUtil(){}

    public DatabaseUtil(String id, String pass, String name, String lastName, String email){
        encryptKey = new HashMap();
        createAdminAccount(id, pass, name, lastName, email);
    }

    private void createAdminAccount(String id, String pass, String name, String lastName, String email){

        if (checkIfAdminExist(email)){
            System.out.println("Account already existing.");
        }else{
            String query = "insert into admin_tbl(id,password,name,lastname,email) values (?,?,?,?,?)";
            try{
                getConnection();
                pst = connection.prepareStatement(query);

                pst.setString(1,id);
                pst.setString(2,encryptPass(pass));
                pst.setString(3,name.trim().substring(0,1).toUpperCase() + name.substring(1));
                pst.setString(4,lastName.trim().substring(0,1).toUpperCase() + lastName.substring(1));
                pst.setString(5,email.trim());
                pst.execute();

                MailUtil.sendMail(email,id,pass);

                System.out.println("\nAccount has been Created.");
                System.out.println("Check your e-mail to get your ID and Password.\n");

            } catch (Exception ex){
                ex.printStackTrace();
            } finally{
                closeConnection();
            }
        }
    }

    private boolean checkIfAdminExist(String email){
        String query = "select * from admin_tbl where email = ?";
        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1,email);
            rs = pst.executeQuery();
            if(rs.next()) return true;

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }

        return false;
    }

    private static void closeConnection(){
        try{
            if(rs != null) rs.close();
            if(pst != null)pst.close();
            if(connection != null)connection.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private static void getConnection(){
        try{
            connection = DriverManager.getConnection(url, userName, password);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static boolean adminVerification(String id, String password){
        String query = "select * from admin_tbl where id = ? and password = ?";

        HashMap encrypt = new HashMap();

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < password.length(); i++){
            encrypt.put(password.charAt(i),Character.getNumericValue(password.charAt(i)));
            sb.append(encrypt.get(password.charAt(i)));
        }
        encrypt.clear();

        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1,id);
            pst.setString(2,sb.toString());
            rs = pst.executeQuery();

            if(rs.next()) return true;

        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
               closeConnection();
        }
        return false;
    }

    public static boolean checkIdClone(String id){
        String query = "select id from admin_tbl where id = ?";
        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1,id);
            rs = pst.executeQuery();

            if(rs.next()) return true;

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }

        return false;
    }

    private static boolean checkPassClone(String pass){

        String query = "select password from admin_tbl where password = ?";

        PreparedStatement preStmt = null;

        try{
            getConnection();
            preStmt = connection.prepareStatement(query);
            preStmt.setString(1,pass);
            rs = preStmt.executeQuery();

            if(rs.next()) return true;

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                preStmt.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return false;
    }

    private static final String encryptPass(String pass){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < pass.length(); i++){
            encryptKey.put(pass.charAt(i),Character.getNumericValue(pass.charAt(i)));
            sb.append(encryptKey.get(pass.charAt(i)));
        }
        encryptKey.clear();

        if(checkPassClone(sb.toString())) return encryptPass(pass);

        return sb.toString();
    }

    public static final String getAdminName(String id){
        String query = "select * from admin_tbl where id = ?";
        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1,id);
            rs = pst.executeQuery();
            if(rs.next()) return rs.getString("name") + " " + rs.getString("lastname");

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }

        return "";
    }

}
