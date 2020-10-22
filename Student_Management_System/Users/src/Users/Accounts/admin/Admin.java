package Users.Accounts.admin;


import Utility.internal.DatabaseUtil;
import students.Student;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.*;

public final class Admin {
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final LocalDateTime timeNow = LocalDateTime.now();
    private final String timeToday = timeFormat.format(timeNow);

    public Admin(String adminName){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime dateNow = LocalDateTime.now();
        String dateToday = dateFormat.format(dateNow);

        boolean activeAdmin = true;
        while(activeAdmin){
            out.println();
            out.print("Welcome, " + adminName + "!");
            out.print("                         " + dateToday);
            out.println();
            out.println("\n---------------------------------------");
            out.println("|             Operations              |");
            out.println("---------------------------------------");
            out.println("| 1. Search         2. Add            |");
            out.println("| 3. Delete         4. Show Student   |");
            out.println("| 5. Enroll         6. Update         |");
            out.println("| 7. Shift          8. Log-out        |");
            out.println("---------------------------------------");

            Scanner scan = new Scanner(in);
            out.print("\nChoose an operation : ");

            switch(scan.nextInt()){
                case 1:
                    out.print("Enter ID : ");
                    if(!searchStudent(scan.nextInt())) out.println("Student not found.");;
                    break;

                case 2:
                    out.print("First Name : ");
                    scan.nextLine();
                    String firstName = scan.nextLine().trim();
                    out.print("Middle Name : ");
                    String middleName = scan.nextLine().trim();
                    out.print("Last Name : ");
                    String lastName = scan.nextLine().trim();
                    out.print("Age : ");
                    int age = scan.nextInt();
                    out.print("Month of Birth : ");
                    String month = scan.next().trim();
                    out.print("Day of Birth : ");
                    int day = scan.nextInt();
                    out.print("Year of Birth : ");
                    int year = scan.nextInt();
                    out.print("Place of Birth :  ");
                    scan.nextLine();
                    String birthPlace = scan.nextLine().trim();
                    out.print("Address : ");
                    String address = scan.nextLine().trim();
                    out.print("Email : ");
                    String email = scan.nextLine().trim();
                    out.print("Contact No. : ");
                    String contact = scan.nextLine().trim();
                    out.print("Regular : ");
                    boolean regular = scan.nextBoolean();
                    out.print("Department : ");
                    String department = scan.next().toUpperCase();
                    out.print("Course : ");
                    scan.nextLine();
                    String course = scan.nextLine();
                    out.print("Year Level : ");
                    String yearLvl = scan.nextLine();
                    out.print("Father's Name : ");
                    String fatherName = scan.nextLine().trim();
                    out.print("Mother's Name : ");
                    String motherName = scan.nextLine();
                    out.print("Contact number of Guardian : ");
                    String contOfGuardian = scan.next().trim();

                    registerStudent(new Student(firstName,middleName,lastName,age,month,day,year,birthPlace,regular,department,course,yearLvl,address,email,contact,dateToday,fatherName,motherName,contOfGuardian));
                    break;

                case 3:
                    out.print("Enter ID : ");
                    int idNumber = scan.nextInt();
                    out.print("Department : ");
                    department = scan.next();
                    if(!searchStudent(idNumber)){
                        out.println("Student not found.");
                        break;
                    }
                    out.print("Are you sure you want to delete Student " + idNumber + " ? ");
                    String confirm = scan.next();
                    if(confirm.equals("yes") | confirm.equals("Yes")) DatabaseUtil.deleteStudent(idNumber,department);
                    else if(confirm.equals("no") | confirm.equals("No")){
                        out.println("Deletion canceled.");
                    }
                    break;

                case 4:
                    ArrayList<Student> students = DatabaseUtil.showStudent();

                    break;

                case 5:
                    out.print("Enter Student ID : ");
                    idNumber = scan.nextInt();
                    if(DatabaseUtil.checkIfEnrolled(idNumber)){
                        out.println("Student is already Enrolled.");
                        break;
                    }
                    if(!searchStudent(idNumber)){
                        out.println("Student not Found.");
                        break;
                    }else{
                        out.print("Department : ");
                        department = scan.next().toUpperCase();
                        out.print("Course : ");
                        scan.nextLine();
                        course = scan.nextLine();
                        out.print("Year level : ");
                        yearLvl = scan.nextLine();
                        enrollStudent(idNumber,department,course,yearLvl);
                        break;
                    }

                case 6:
                    out.print("Enter ID : ");
                    idNumber = scan.nextInt();
                    searchStudent(idNumber);
                    out.println("\nUpdate into...");
                    out.print("Fullname : ");
                    scan.nextLine();
                    String fullname = scan.nextLine();
                    out.print("Age : ");
                    age = scan.nextInt();
                    out.print("Birthdate : ");
                    scan.nextLine();
                    String birthDate = scan.nextLine();
                    out.print("Birthplace : ");
                    birthPlace = scan.nextLine();
                    out.print("Address : ");
                    address = scan.nextLine();
                    out.print("E-mail : ");
                    email = scan.next();
                    out.print("Contact : ");
                    contact = scan.next();
                    out.print("Regular : ");
                    regular = scan.nextBoolean();
                    out.print("Year level : ");
                    scan.nextLine();
                    yearLvl = scan.nextLine();
                    out.print("Father's Name : ");
                    fatherName = scan.nextLine();
                    out.print("Mother's Name : ");
                    motherName = scan.nextLine();
                    out.print("Contact no. of Guardian : ");
                    contOfGuardian = scan.next();

                    DatabaseUtil.updateStudent(idNumber,fullname,age,birthDate,birthPlace,address,email,contact,regular,yearLvl,fatherName,motherName,contOfGuardian);

                    break;

                case 7:
                    out.print("Enter ID : ");
                    idNumber = scan.nextInt();
                    out.print("Department : ");
                    department = scan.next();
                    out.print("Course : ");
                    scan.nextLine();
                    course = scan.nextLine();
                    out.print("Year level : ");
                    yearLvl = scan.nextLine();
                    DatabaseUtil.shiftStudent(idNumber, department, course, yearLvl);
                    break;

                case 8:
                    out.println(adminName + " Signing out...");
                    activeAdmin = false;
                    break;
            }
        }
    }

    public void registerStudent(Student student){
        DatabaseUtil.addStudent(student);
    }

    public boolean searchStudent(int idNumber){
        Student student = DatabaseUtil.searchStudent(idNumber);

        if(student == null){
            return false;
        }else{
            out.println("ID : " + student.getId());
            out.println("Fullname : " + student.getFullName());
            out.println("Age : " + student.getAge());
            out.println("Birthdate : " + student.getBirthDate());
            out.println("Birthplace : " + student.getBirthPlace());
            out.println("Address : " + student.getAddress());
            out.println("E-mail : " + student.getEmail());
            out.println("Contact : " + student.getContact());
            boolean regular = student.isRegular() == 1;
            out.println("Regular : " + regular);
            out.println("Department : " + student.getDepartment());
            out.println("Course : " + student.getCourse());
            out.println("Year Level : " + student.getYearLvl());
            out.println("Status : " + student.getStatus());
            out.println("Date Registered : " + student.getDateRegistered());
            out.println("Father's Name : " + student.getFatherName());
            out.println("Mother's Name : " + student.getMotherName());
            out.println("Contact no. of Guardian : " + student.getContOfGuardian());
            return true;
        }
    }

    public void enrollStudent(int idNumber,String department, String course, String yearLvl){
        DatabaseUtil.enrollStudent(idNumber,department,course,yearLvl);
    }

    // U2Yo7-Uv7sF-gwuv2
    // 33%6)?3!

}
