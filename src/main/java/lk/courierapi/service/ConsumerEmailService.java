package lk.courierapi.service;

import lk.courierapi.dto.EmailDTO;

public interface ConsumerEmailService {
    void sendEmail(EmailDTO emailDTO);
}
