package lk.courierapi.service;

import lk.courierapi.dto.EmailDTO;

public interface ConsumerService {
    void consume(EmailDTO emailDTO) throws Exception;
}
