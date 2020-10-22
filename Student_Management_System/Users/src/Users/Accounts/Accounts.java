package Users.Accounts;

import Utility.internal.DatabaseUtil;

public class Accounts{

    public Accounts(String id, String pass, String name, String lastName, String email){
        new DatabaseUtil(id, pass, name, lastName, email);
    }

    public static boolean checkAdmin(String id, String password){
        return DatabaseUtil.adminVerification(id,password);
    }

    public static String getAdminName(String id){

        return DatabaseUtil.getAdminName(id);
    }

    public static void createAdminAccount(String email, String name, String lastName){
        new DatabaseUtil(generateID(), generatePass(), name, lastName, email);
    }

    private static final String generateID(){
        String set = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder id = new StringBuilder();

        for(int i = 1; i < 16; i++){
            id.append(set.charAt((int) (Math.random() * set.length())));
            if(i != 15 && i % 5 == 0){
                id.append("-");
            }
        }

        if(DatabaseUtil.checkIdClone(id.toString())) generateID();

        return id.toString();
    }

    private static final String generatePass(){
        String set = "1234567890?&*^%$#@!()";
        StringBuilder pass = new StringBuilder();

        for(int i = 0; i < 8; i++){
            pass.append(set.charAt((int) (Math.random() * set.length())));
        }
        return pass.toString();
    }

}
