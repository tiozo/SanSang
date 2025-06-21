package utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;            // Added missing import
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {
    
    public static void sendEmail(String host, String port,
            final String userName, final String password,
            String toAddress, String subject, String messageBody)
            throws MessagingException {
        
        // Set SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Create a new session with an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        
        Session session = Session.getInstance(props, auth);
        
        // Create a new email message
        Message msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(userName));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(messageBody);
        
        // Send the email
        Transport.send(msg);
    }
}
