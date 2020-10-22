package students;

public class Student {
    private int id;
    private int age;
    private String birthDate;
    private String fullName;
    private boolean regular;
    private String birthPlace;
    private String email;
    private String contact;
    private String address;
    private String contOfGuardian;
    private String fatherName;
    private String motherName;
    private String dateRegistered;
    private String department;
    private String course;
    private String yearLvl;
    private String status = "Not-enrolled";

    public Student(int id, String fullname, int age, String birthDate, String birthPlace, int regular,String department,String course,String yearLvl, String status,String address, String email, String contact, String dateRegistered, String father, String mother, String contOfGuardian){
        this.id = id;
        this.fullName = fullname;
        this.birthDate = birthDate;
        this.age = age;
        this.regular = regular == 1;
        this.birthPlace = birthPlace;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.contOfGuardian = contOfGuardian;
        this.fatherName = father;
        this.motherName = mother;
        this.dateRegistered = dateRegistered;
        this.department = department;
        this.course = course;
        this.yearLvl = yearLvl;
        this.status = status;
    }

    public Student(String name, String middleName, String lastName, int age, String month, int day, int year, String birthPlace, boolean regular,String department, String course, String yearLvl, String address, String email, String contact, String dateRegistered,String father, String mother, String contOfGuardian){
        name = name.trim().substring(0,1).toUpperCase() + name.substring(1);
        middleName = middleName.trim().substring(0,1).toUpperCase() + middleName.substring(1);
        lastName = lastName.trim().substring(0,1).toUpperCase() + lastName.substring(1);
        fullName = name + " " + middleName + " " + lastName;
        birthDate = month.trim().substring(0,1).toUpperCase() + month.substring(1) + " " + day + ", " + year;
        this.birthPlace = birthPlace;
        this.age = age;
        this.regular = regular;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.contOfGuardian = contOfGuardian;
        this.fatherName = father;
        this.motherName = mother;
        this.birthPlace = birthPlace;
        this.dateRegistered = dateRegistered;
        this.department = department;
        this.course = course;
        this.yearLvl = yearLvl;
    }

    public String getDepartment(){
        return department;
    }

    public String getYearLvl(){
        return yearLvl;
    }

    public String getStatus(){
        return status;
    }

    public String getCourse(){
        return course;
    }

    public int getId(){
        return id;
    }

    public String getDateRegistered(){
        return dateRegistered;
    }

    public String getBirthPlace(){
        return birthPlace;
    }

    public String getContOfGuardian(){
        return contOfGuardian;
    }

    public String getFatherName(){
        return fatherName;
    }

    public String getMotherName(){
        return motherName;
    }

    public String getBirthDate(){
        return birthDate;
    }

    public int getAge(){
        return age;
    }

    public String getFullName(){
        return fullName;
    }

    public int isRegular(){
        return regular ? 1 : 0;
    }

    public String getEmail(){
        return email;
    }

    public String getAddress(){
        return address;
    }

    public String getContact(){
        return contact;
    }


}
