package lk.courierapi.service;

import lk.courierapi.dto.OrderDetailDTOV2;
import lk.courierapi.exception.NotFoundException;

import java.util.List;


public interface OrderDetailServiceV2 {

    OrderDetailDTOV2 placeOrder(OrderDetailDTOV2 orderDetailDTOV2) throws Exception;

    OrderDetailDTOV2 editOrder(OrderDetailDTOV2 orderDetailDTOV2) throws Exception;

    void deleteOrder(Long id) throws Exception;

    List<OrderDetailDTOV2> getOrders() throws NotFoundException;

}
