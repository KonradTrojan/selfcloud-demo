package pl.trojan.selfcloud.demo.listener;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.trojan.selfcloud.demo.event.OnRegistrationCompleteEvent;
import pl.trojan.selfcloud.demo.model.User;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
            this.confirmRegistration(event);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        User user = event.getUser();

        String htmlContent = "<h1>Hello " + user.getUsername() + "!</h1>" +
                "<p>It can contain <strong>HTML</strong> content.</p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");
        message.setRecipients(MimeMessage.RecipientType.TO, user.getMail());
        message.setSubject("Registration confirmation");

        mailSender.send(message);
    }
}
