package lk.courierapi.service.impl;

import lk.courierapi.dto.CourierCompanyDTO;
import lk.courierapi.dto.OrderDetailDTO;
import lk.courierapi.exception.NotFoundException;
import lk.courierapi.exception.ValidateException;
import lk.courierapi.service.OrderDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderDetailServiceImplTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @Test
    void placeOrderTest1(){//Place order with all details
        OrderDetailDTO detailDTO= new OrderDetailDTO(1,"Nimal","Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO("C001","RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertDoesNotThrow( () -> {
                orderDetailService.placeOrder(detailDTO);
        });
    }

    @Test
    void placeOrderTest2(){//Place order without Courier company code
        OrderDetailDTO detailDTO= new OrderDetailDTO(1,"Nimal","Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO(null,"RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertThrows(ValidateException.class,() -> {
            orderDetailService.placeOrder(detailDTO);
        });
    }

    @Test
    void placeOrderTest3(){//Place order with null values
        OrderDetailDTO detailDTO= new OrderDetailDTO(1,null,"Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO("C001","RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertThrows(MappingException.class,() -> {
            orderDetailService.placeOrder(detailDTO);
        });
    }

    @Test
    void placeOrderTest4(){//Place order with unavailable company code
        OrderDetailDTO detailDTO= new OrderDetailDTO(1,"Namal","Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO("C002","RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertThrows(NotFoundException.class, () -> {
            orderDetailService.placeOrder(detailDTO);
        });
    }


    @Test
    void updateOrderTest1(){//Update order with all details
        OrderDetailDTO detailDTO= new OrderDetailDTO(3,"Nimal","Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO("C001","RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertDoesNotThrow( () -> {
            orderDetailService.editOrder(detailDTO);
        });
    }

    @Test
    void updateOrderTest2(){//Update order without Courier company code
        OrderDetailDTO detailDTO= new OrderDetailDTO(3,"Nimal","Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO(null,"RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertThrows(ValidateException.class,() -> {
            orderDetailService.editOrder(detailDTO);
        });
    }

    @Test
    void updateOrderTest3(){//Update order with null values
        OrderDetailDTO detailDTO= new OrderDetailDTO(3,null,"Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO("C001","RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertThrows(MappingException.class,() -> {
            orderDetailService.editOrder(detailDTO);
        });
    }

    @Test
    void updateOrderTest4(){//Update order with unavailable company code
        OrderDetailDTO detailDTO= new OrderDetailDTO(3,"Namal","Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO("C002","RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertThrows(NotFoundException.class, () -> {
            orderDetailService.editOrder(detailDTO);
        });
    }

    @Test
    void updateOrderTest5(){//Update order with unavailable order id
        OrderDetailDTO detailDTO= new OrderDetailDTO(1,"Namal","Badulla","Nimal","Badulla",100.0,new CourierCompanyDTO("C001","RPOST","Badulla","ravindulakshan877@gmail.com","0781816652"));
        Assertions.assertThrows(NotFoundException.class, () -> {
            orderDetailService.editOrder(detailDTO);
        });
    }

    @Test
    void deleteOrderTest1(){//Delete order with correct id
        Assertions.assertDoesNotThrow(() -> {
            orderDetailService.deleteOrder(3L);
        });
    }

    @Test
    void deleteOrderTest2(){//Delete order with unavailable id
        Assertions.assertThrows(NotFoundException.class,() -> {
            orderDetailService.deleteOrder(1L);
        });
    }

}
