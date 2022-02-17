package lk.courierapi.controller;

import lk.courierapi.dto.OrderDetailDTOV2;
import lk.courierapi.exception.NotFoundException;
import lk.courierapi.exception.ValidateException;
import lk.courierapi.service.OrderDetailServiceV2;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("V2/order")
@CrossOrigin
public class OrderDetailApiV2 {

    private final OrderDetailServiceV2 orderDetailServiceV2;

    @Autowired
    public OrderDetailApiV2(OrderDetailServiceV2 orderDetailServiceV2) {
        this.orderDetailServiceV2 = orderDetailServiceV2;
    }

    @PostMapping("/add")
    public ResponseEntity<OrderDetailDTOV2> placeOrder(@RequestBody OrderDetailDTOV2 orderDetailDTOV2){
        try {
            return ResponseEntity.ok(orderDetailServiceV2.placeOrder(orderDetailDTOV2));
        }catch (ValidateException | MappingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDetailDTOV2);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderDetailDTOV2);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderDetailDTOV2);
        }
    }

    @DeleteMapping(params = {"orderId"})
    public ResponseEntity<String> deleteOrder(@RequestParam Long orderId){
        try {
            orderDetailServiceV2.deleteOrder(orderId);
            return ResponseEntity.ok(null);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (ValidateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<OrderDetailDTOV2> editOrder(@RequestBody OrderDetailDTOV2 orderDetailDTOV2) {
        try {
            return ResponseEntity.ok(orderDetailServiceV2.editOrder(orderDetailDTOV2));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderDetailDTOV2);
        }catch (ValidateException | MappingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDetailDTOV2);
        }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderDetailDTOV2);
        }
    }


    @GetMapping
    public ResponseEntity<List<OrderDetailDTOV2>> getOrders(){
        try {
            return ResponseEntity.ok(orderDetailServiceV2.getOrders());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
