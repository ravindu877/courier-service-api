package lk.courierapi.service;

import lk.courierapi.dto.OrderDetailDTO;
import lk.courierapi.exception.NotFoundException;

import java.util.List;


public interface OrderDetailService {

    OrderDetailDTO placeOrder(OrderDetailDTO orderDetailDTO) throws Exception;

    OrderDetailDTO editOrder(OrderDetailDTO orderDetailDTO) throws Exception;

    void deleteOrder(Long id) throws Exception;

    List<OrderDetailDTO> getOrders() throws NotFoundException;

}
