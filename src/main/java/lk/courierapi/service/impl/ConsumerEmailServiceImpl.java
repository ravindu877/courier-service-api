package lk.courierapi.service.impl;

import lk.courierapi.dto.EmailDTO;
import lk.courierapi.service.ConsumerEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ConsumerEmailServiceImpl implements ConsumerEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async
    public void sendEmail(EmailDTO emailDTO) {
        SimpleMailMessage mailMessage= new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(emailDTO.getToEmail());
        mailMessage.setSubject(emailDTO.getSubject());
        mailMessage.setText(emailDTO.getBody());
        mailSender.send(mailMessage);
    }
}
