package mail;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 *
 * @author Windy
 */
public class SendEmail {

    private static Properties SSLProp = new Properties();
    private static Properties TLSProp = new Properties();
    private static String fromGmail = "mail@gmail.com";
    protected static final String username = "mail@gmail.com";
    protected static final String password = "password";

    static {
        SSLProp.put("mail.smtp.host", "smtp.gmail.com");
        SSLProp.put("mail.smtp.port", "465");
        SSLProp.put("mail.smtp.auth", "true");
        SSLProp.put("mail.smtp.socketFactory.port", "465");
        SSLProp.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        TLSProp.put("mail.smtp.host", "smtp.gmail.com");
        TLSProp.put("mail.smtp.port", "587");
        TLSProp.put("mail.smtp.auth", "true");
        TLSProp.put("mail.smtp.starttls.enable", "true");
    }

    // dont use
    public static void sendBySSL(final String subject, final String text, final List<String> toMails) {
        String addressList = "";
        for (String mail : toMails) {
            addressList += mail += ",";
        }
        addressList = addressList.substring(0, addressList.length() - 1);
        Session session = Session.getInstance(SSLProp,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromGmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressList));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("[SSL信箱-發送] 收信者[" + addressList + "] 主旨[" + subject + "] 內容:" + text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendByTLS(final String subject, final String text, final List<String> toMails) {
        String addressList = "";
        for (String mail : toMails) {
            addressList += mail += ",";
        }
        addressList = addressList.substring(0, addressList.length() - 1);
        Session session = Session.getInstance(TLSProp,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromGmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressList));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("[TLS信箱-發送] 收信者[" + addressList + "] 主旨[" + subject + "] 內容:" + text);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendBySSL("主旨", "內容", Arrays.asList("test@hotmail.com"));
        sendByTLS("主旨", "內容2", Arrays.asList("test@hotmail.com"));
    }

}
