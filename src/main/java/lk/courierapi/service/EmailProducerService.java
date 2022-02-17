package lk.courierapi.service;

import lk.courierapi.dto.EmailDTO;

public interface EmailProducerService {
    void sendEmail(EmailDTO emailDTO);
}
