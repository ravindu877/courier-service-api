package lk.courierapi.service.impl;

import lk.courierapi.dto.EmailDTO;
import lk.courierapi.service.ConsumerEmailService;
import lk.courierapi.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerEmailService emailService;

    @Override
    @KafkaListener(topics = "${topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(EmailDTO emailDTO) throws Exception {
        try {
            emailService.sendEmail(emailDTO);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
