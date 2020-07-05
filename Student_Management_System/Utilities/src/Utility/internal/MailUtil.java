package Utility.internal;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

final class MailUtil{
    private static final String  email = "rtechnology121@gmail.com", password = "Tae12345";

    public static void sendMail(String recepient, String id, String newPassword) throws Exception{
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        Message message = prepareMessage(session, email, recepient, id, newPassword);

        Transport.send(message);

    }

    private static Message prepareMessage(Session session, String email, String recepient, String id, String password){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Registration");
            message.setText("You have officially Registered as an Admin in RTechnology Corp.\n\nYour ID is : " + id + "\nYour Password is : " + password);
            return message;
        }catch (Exception ex){
            System.out.println("Please enter your e-mail.");
        }

        return null;
    }

}
