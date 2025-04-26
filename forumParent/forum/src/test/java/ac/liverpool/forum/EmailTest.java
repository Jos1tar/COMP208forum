package ac.liverpool.forum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    void testPersonalEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("riot167325901@163.com");
        message.setTo("hj534029@gmail.com");
        message.setSubject("Personal Email Test");
        message.setText("This is a test email sent from the personal email account.");

        try {
            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (MailException e) {
            System.err.println("Failed to send email.");
            e.printStackTrace();
        }
    }
}
