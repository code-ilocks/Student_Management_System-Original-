package Runner;

import Users.Accounts.Accounts;
import Users.Accounts.admin.Admin;

import java.util.Scanner;
import static java.lang.System.out;

public class Runner {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        do{
            out.println();
            out.print("Press 'Enter' if you already have an account.\nPress any key if you still haven't : ");
            if (scan.nextLine().equals("")) {
                out.print("ID : ");
                String id = scan.next();
                out.print("Password : ");
                String password = scan.next();

                if(Accounts.checkAdmin(id,password)){
                    new Admin(Accounts.getAdminName(id));
                }else{
                    out.println("Wrong input.");
                }
            } else{
                out.println();
                out.print("E-mail : ");
                String eMail = scan.next();
                out.print("First Name : ");
                scan.nextLine();
                String name = scan.nextLine();
                out.print("Last Name : ");
                String lastName = scan.nextLine();

                Accounts.createAdminAccount(eMail, name, lastName);
                out.print("Press Enter to continue... ");
            }
        }while(scan.nextLine().equals(""));
    }

}
