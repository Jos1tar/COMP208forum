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
        message.setSubject("个人邮箱测试");
        message.setText("这是一封来自个人邮箱的测试邮件");

        try {
            mailSender.send(message);
            System.out.println("邮件发送成功");
        } catch (MailException e) {
            System.err.println("邮件发送失败:");
            e.printStackTrace();
        }
    }
}
