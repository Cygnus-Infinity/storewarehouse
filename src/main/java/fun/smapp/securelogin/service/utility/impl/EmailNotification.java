package fun.smapp.securelogin.service.utility.impl;

import fun.smapp.securelogin.service.utility.Message;
import fun.smapp.securelogin.service.utility.Notification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailNotification implements Notification {

    private JavaMailSender javaMailSender;
    private SimpleMailMessage simpleMailMessage;
    private Message message;

    /**
     * Dependency Injection
     * @param javaMailSender
     * @param simpleMailMessage
     * @param message
     */
    public EmailNotification(JavaMailSender javaMailSender, SimpleMailMessage simpleMailMessage, Message message) {
        this.javaMailSender = javaMailSender;
        this.simpleMailMessage = simpleMailMessage;
        this.message = message;
    }

    @Override
    public Boolean send(String to, String from, String content, String subject) {

        try{

            MimeMessage message = javaMailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);

            // use the true flag to indicate the text included is HTML
            helper.setText(content, true);

            javaMailSender.send(message);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }
}
