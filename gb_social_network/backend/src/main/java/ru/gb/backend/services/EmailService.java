package ru.gb.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.backend.entity.User;
import ru.gb.backend.utils.EmailAuthenticator;
import ru.gb.backend.utils.SimpleEmail;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailService {
    @Value("${email.server}")
    private String SMTP_SERVER;
    @Value("${email.port}")
    private String SMTP_PORT;
    @Value("${email.username}")
    private String SMTP_AUTH_USER;
    @Value("${email.password}")
    private String SMTP_AUTH_PWD;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private int port;
    private Session session;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_SERVER);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        Authenticator auth = new EmailAuthenticator(SMTP_AUTH_USER,
                SMTP_AUTH_PWD);
        session = Session.getDefaultInstance(props, auth);
        session.setDebug(false);
    }

    public boolean sendMessage(SimpleEmail mail) {
        boolean result = false;
        try {
            InternetAddress email_from = new InternetAddress("snsupp@yandex.ru");
            InternetAddress email_to = new InternetAddress(mail.getMailTo());
            Message message = new MimeMessage(session);
            message.setFrom(email_from);
            message.setRecipient(Message.RecipientType.TO, email_to);
            message.setSubject(mail.getSubject());

            // Содержимое сообщения
            Multipart mmp = new MimeMultipart();
            // Текст сообщения
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(mail.getMessage(), "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            message.setContent(mmp);
            // Отправка сообщения
            Transport.send(message);
            result = true;
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void constructAndSendResetTokenEmail(String token, User user) {
        String url = "http://localhost:" + port + contextPath + "/api/v1/users/changePassword?token=" + token;
        sendMessage(SimpleEmail.builder()
                .subject("Reset Password")
                .message(user.getName() + " от Вас был получен запрос на сброс пароля, если Вы не запрашивали сброс пароля, проигнорируйте данное письмо, если же Вы действительно хотите поменять пароль, то пройдите по ссылке ниже\r\n" + url)
                .mailTo(user.getEmail())
                .build()
        );
    }
}
