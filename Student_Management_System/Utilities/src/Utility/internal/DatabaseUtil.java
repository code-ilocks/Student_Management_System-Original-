package Utility.internal;

import students.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.out;

public class DatabaseUtil {
    private static Connection connection = null;
    private static final String url = "jdbc:mysql://localhost/student_management?serverTimezone=UTC";
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

    public static final Student searchStudent(int idNumber){
        String query = "SELECT * FROM `student_tbl` WHERE id = ?";
        Student student = null;
        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1,idNumber);
            rs = pst.executeQuery();

            if(rs.next()){
                int id = idNumber;
                String fullname = rs.getString("fullname");
                int age = rs.getInt("age");
                String birthPlace = rs.getString("birthplace");
                String birthDate = rs.getString("birthDate");
                int isRegular = rs.getInt("regular");
                String department = rs.getString("department");
                String course = rs.getString("course");
                String yearlvl = rs.getString("year_lvl");
                String address = rs.getString("address");
                String email = rs.getString("email");
                String contact = rs.getString("contact");
                String dateRegistered = rs.getString("date_registered");
                String father = rs.getString("father");
                String mother = rs.getString("mother");
                String guardianContact = rs.getString("guardian_contact");
                String status = rs.getString("status");

                return new Student(id,fullname,age,birthDate,birthPlace,isRegular,department,course,yearlvl,status,address,email,contact,dateRegistered,father,mother,guardianContact);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }

        return student;
    }

    public static final void addStudent(Student student){
        String studentQuery = "insert into student_tbl(fullname, birthdate, age, address, email, contact, birthplace, regular, department, course, year_lvl, status, date_registered, father, mother, guardian_contact) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try{
            getConnection();
            pst = connection.prepareStatement(studentQuery);
            pst.setString(1,student.getFullName());
            pst.setString(2,student.getBirthDate());
            pst.setInt(3,student.getAge());
            pst.setString(4,student.getAddress());
            pst.setString(5,student.getEmail());
            pst.setString(6,student.getContact());
            pst.setString(7,student.getBirthPlace());
            pst.setInt(8,student.isRegular());
            pst.setString(9,student.getDepartment());
            pst.setString(10,student.getCourse());
            pst.setString(11,student.getYearLvl());
            pst.setString(12,student.getStatus());
            pst.setString(13,student.getDateRegistered());
            pst.setString(14,student.getFatherName());
            pst.setString(15,student.getMotherName());
            pst.setString(16,student.getContOfGuardian());
            pst.execute();

            System.out.println("Student Added.");

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
    }

    public static final void enrollStudent(int idNumber, String department, String course, String yearLvl){
        department += "_department";
        String getNameQuery = "Select * from student_tbl where id = ?";
        String enrollStudent = "Insert into " + department + "(student_id,fullname,course,year_lvl) values(?,?,?,?)";

        try{
            getConnection();
            pst = connection.prepareStatement(getNameQuery);
            pst.setInt(1,idNumber);
            rs = pst.executeQuery();
            rs.next();

            StringBuilder fullname = new StringBuilder();
            fullname.append(rs.getString("fullname"));

            pst = connection.prepareStatement(enrollStudent);
            pst.setInt(1,idNumber);
            pst.setString(2,fullname.toString());
            pst.setString(3,course);
            pst.setString(4,yearLvl);
            pst.execute();


        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try{
                pst = connection.prepareStatement("update student_tbl set status = 'Enrolled' where student_tbl.id = ?");
                pst.setInt(1,idNumber);
                pst.execute();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
            finally {
                closeConnection();
            }
        }
    }

    public static final boolean checkIfEnrolled(int idNumber){
        String query = "Select * from student_tbl where id = ?";
        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1,idNumber);
            rs = pst.executeQuery();
            rs.next();

            return rs.getString("status").equals("Enrolled");

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return true;
    }

    public static final void deleteStudent(int idNumber, String department){
        department += "_department";
        String childrenTable = "delete from " + department + " where student_id = ?";
        String parentTable = "delete from student_tbl where id = ?";

        try{
            getConnection();
            pst = connection.prepareStatement(childrenTable);
            pst.setInt(1,idNumber);
            pst.execute();
            pst = connection.prepareStatement(parentTable);
            pst.setInt(1,idNumber);
            pst.execute();
            out.println("Deletion Successful.");
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }

    }

    public static final ArrayList<Student> showStudent(){

        return null;
    }

    public static final void updateStudent(int id,String fullname,int age,String birthDate,String birthPlace,String address,String email,String contact,boolean regular,String yearLvl,String fatherName,String motherName,String contOfGuardian){
        String query = "update student_tbl set fullname = ?, age = ?, birthdate = ?, birthplace = ?, address = ?, email = ?, contact = ?, regular = ?,year_lvl = ?, father = ?, mother = ?, guardian_contact = ? where id = " + id;

        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setString(1,fullname);
            pst.setInt(2,age);
            pst.setString(3,birthDate);
            pst.setString(4,birthPlace);
            pst.setString(5,address);
            pst.setString(6,email);
            pst.setString(7,contact);
            pst.setInt(8,regular ? 1 : 0);
            pst.setString(9,yearLvl);
            pst.setString(10,fatherName);
            pst.setString(11,motherName);
            pst.setString(12,contOfGuardian);
            pst.executeUpdate();

            out.println("Update Successful.");

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }


    }

    public static final void shiftStudent(int idNumber,String department, String course, String yearLvl){
        String query = "update student_tbl set department = " + department + ", course = " + course + ", year_lvl = " + yearLvl + ", where id = ?";

        try{
            getConnection();
            pst = connection.prepareStatement(query);
            pst.setInt(1,idNumber);
            pst.execute();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }

    }
}