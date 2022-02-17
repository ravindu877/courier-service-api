package lk.courierapi.service.impl;


import lk.courierapi.dto.EmailDTO;
import lk.courierapi.dto.OrderDetailDTO;
import lk.courierapi.entity.CourierCompany;
import lk.courierapi.entity.OrderDetail;
import lk.courierapi.exception.NotFoundException;
import lk.courierapi.exception.ValidateException;
import lk.courierapi.repository.CourierCompanyRepo;
import lk.courierapi.repository.OrderDetailRepo;
import lk.courierapi.service.OrderDetailService;
import lk.courierapi.util.EmailService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private CourierCompanyRepo courierCompanyRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDetailDTO placeOrder(OrderDetailDTO orderDetailDTO) throws Exception {

        if (null == orderDetailDTO.getCourierCompanyDTO().getCode() || orderDetailDTO.getCourierCompanyDTO().getCode().isEmpty()){
            throw new ValidateException("Company code required");
        }

        if (courierCompanyRepo.findById(orderDetailDTO.getCourierCompanyDTO().getCode()).isEmpty()){
            throw new NotFoundException("Record not found");
        }

        try {
            orderDetailRepo.save(mapper.map(orderDetailDTO, OrderDetail.class));

            CourierCompany byCode = courierCompanyRepo.findByCode(orderDetailDTO.getCourierCompanyDTO().getCode());


            StringBuilder emailBody= new StringBuilder("you have received a order from "+ orderDetailDTO.getSenderName()+"" +
                    ","+  orderDetailDTO.getSenderAddress() +". Please pick the package and deliver to "+ orderDetailDTO.getReceiverName() +"" +
                    " , " +orderDetailDTO.getReceiverAddress()+".");

            EmailDTO emailDTO= new EmailDTO();
            emailDTO.setToEmail(byCode.getEmail());
            emailDTO.setBody(emailBody.toString());
            emailDTO.setSubject("Delivery Order");

            emailService.sendEmail(emailDTO);

        }catch (MappingException e){
            List<ErrorMessage> errorMessages= new ArrayList<>(e.getErrorMessages());
            throw new MappingException(errorMessages);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return orderDetailDTO;
    }

    @Override
    public OrderDetailDTO editOrder(OrderDetailDTO orderDetailDTO) throws Exception {

        if(null == orderDetailDTO.getCourierCompanyDTO().getCode() || orderDetailDTO.getCourierCompanyDTO().getCode().isEmpty()){
            throw new ValidateException("Company code required");
        }

        orderDetailRepo.findById(orderDetailDTO.getOrderId()).orElseThrow(()->{
            throw new NotFoundException("Record not found");
        });

        courierCompanyRepo.findById(orderDetailDTO.getCourierCompanyDTO().getCode()).orElseThrow(()->{
            throw new NotFoundException("Record not found");
        });

        try {
            orderDetailRepo.save(mapper.map(orderDetailDTO, OrderDetail.class));
        }catch (MappingException e){
            List<ErrorMessage> errorMessages= new ArrayList<>(e.getErrorMessages());
            throw new MappingException(errorMessages);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return orderDetailDTO;
    }

    @Override
    public void deleteOrder(Long id) throws Exception {

        if(null == id){
            throw new ValidateException("Order id required");
        }

        orderDetailRepo.findById(id).orElseThrow(()->{
            throw new NotFoundException("Record not found");
        });

       try {
           orderDetailRepo.deleteById(id);
       }catch (Exception e){
           throw new Exception(e.getMessage());
       }


    }

    @Override
    public List<OrderDetailDTO> getOrders() throws NotFoundException {
        List<OrderDetail> orderDetails= orderDetailRepo.findAll();
        if (orderDetails.isEmpty()){
            throw new NotFoundException("Record not found");
        }else {
            return mapper.map(orderDetails,new TypeToken<ArrayList<OrderDetailDTO>>(){}.getType());
        }
    }
}
