package lk.courierapi.controller;

import lk.courierapi.dto.OrderDetailDTO;
import lk.courierapi.exception.NotFoundException;
import lk.courierapi.exception.ValidateException;
import lk.courierapi.service.OrderDetailService;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("V1/order")
@CrossOrigin
public class OrderDetailApi {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailApi(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping("/add")
    public ResponseEntity<OrderDetailDTO> placeOrder(@RequestBody OrderDetailDTO orderDetailDTO){
        try {
            return ResponseEntity.ok(orderDetailService.placeOrder(orderDetailDTO));
        }catch (ValidateException | MappingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDetailDTO);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderDetailDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderDetailDTO);
        }
    }

    @DeleteMapping(params = {"orderId"})
    public ResponseEntity<String> deleteOrder(@RequestParam Long orderId){
        try {
            orderDetailService.deleteOrder(orderId);
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
    public ResponseEntity<OrderDetailDTO> editOrder(@RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            return ResponseEntity.ok(orderDetailService.editOrder(orderDetailDTO));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderDetailDTO);
        }catch (ValidateException | MappingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDetailDTO);
        }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderDetailDTO);
        }
    }


    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getOrders(){
        try {
            return ResponseEntity.ok(orderDetailService.getOrders());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
