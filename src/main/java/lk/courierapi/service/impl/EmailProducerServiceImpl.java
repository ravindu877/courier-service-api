package lk.courierapi.service.impl;

import lk.courierapi.dto.EmailDTO;
import lk.courierapi.service.EmailProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducerServiceImpl implements EmailProducerService {

    @Autowired
    private KafkaTemplate<String, EmailDTO> kafkaTemplate;

    @Value("${topic}")
    String emailTopic;

    @Override
    public void sendEmail(EmailDTO emailDTO) {
        kafkaTemplate.send(emailTopic, emailDTO);
    }
}
