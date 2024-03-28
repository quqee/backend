package org.hits.backend.hackaton.core.mail;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {
    private final EmailProperties emailProperties;

    public void sendMessage(String text, String to, String subject) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", emailProperties.smtp().auth());
        props.put("mail.smtp.starttls.enable", emailProperties.smtp().starttls().enable());
        props.put("mail.smtp.host", emailProperties.smtp().host());
        props.put("mail.smtp.port", emailProperties.smtp().port());
        props.put("mail.debug", true);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailProperties.username(), emailProperties.password());
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailProperties.from()));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setContent(text, "text/html");

            Transport.send(msg);
        } catch (MessagingException e) {
            throw new ExceptionInApplication("Can't send message", ExceptionType.INVALID);
        }
    }
}
