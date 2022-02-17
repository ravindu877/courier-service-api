package lk.courierapi.service.impl;


import lk.courierapi.dto.EmailDTO;
import lk.courierapi.dto.OrderDetailDTOV2;
import lk.courierapi.entity.CourierCompany;
import lk.courierapi.entity.OrderDetailV2;
import lk.courierapi.exception.NotFoundException;
import lk.courierapi.exception.ValidateException;
import lk.courierapi.repository.CourierCompanyRepo;
import lk.courierapi.repository.OrderDetailRepoV2;
import lk.courierapi.service.OrderDetailServiceV2;
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
public class OrderDetailServiceImplV2 implements OrderDetailServiceV2 {

    @Autowired
    private OrderDetailRepoV2 orderDetailRepoV2;

    @Autowired
    private CourierCompanyRepo courierCompanyRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDetailDTOV2 placeOrder(OrderDetailDTOV2 orderDetailDTOV2) throws Exception {

        if (null == orderDetailDTOV2.getCourierCompanyDTO().getCode() || orderDetailDTOV2.getCourierCompanyDTO().getCode().isEmpty()){
            throw new ValidateException("Company code required");
        }

        if (courierCompanyRepo.findById(orderDetailDTOV2.getCourierCompanyDTO().getCode()).isEmpty()){
            throw new NotFoundException("Record not found");
        }

        try {
            orderDetailRepoV2.save(mapper.map(orderDetailDTOV2, OrderDetailV2.class));


            CourierCompany byCode = courierCompanyRepo.findByCode(orderDetailDTOV2.getCourierCompanyDTO().getCode());


            StringBuilder emailBody= new StringBuilder("you have received a order from "+ orderDetailDTOV2.getSenderName()+"" +
                    ","+  orderDetailDTOV2.getSenderAddress() +". Parcel type is "+ orderDetailDTOV2.getParcelType() +". Please pick the package and deliver to "+ orderDetailDTOV2.getReceiverName() +"" +
                    " , " +orderDetailDTOV2.getReceiverAddress()+".");

            EmailDTO emailDTO= new EmailDTO();
            emailDTO.setToEmail(byCode.getEmail());
            emailDTO.setBody(emailBody.toString());
            emailDTO.setSubject("Delivery Order");

            emailService.sendEmail(emailDTO);

        }catch (MappingException e){
            List<ErrorMessage> errorMessages= new ArrayList<>(e.getErrorMessages());
            throw new MappingException(errorMessages);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return orderDetailDTOV2;
    }

    @Override
    public OrderDetailDTOV2 editOrder(OrderDetailDTOV2 orderDetailDTOV2) throws Exception {

        if(null == orderDetailDTOV2.getCourierCompanyDTO().getCode() || orderDetailDTOV2.getCourierCompanyDTO().getCode().isEmpty()){
            throw new ValidateException("Company code required");
        }

        orderDetailRepoV2.findById(orderDetailDTOV2.getOrderId()).filter(obj -> false).orElseThrow(()->{
            throw new NotFoundException("Record not found");
        });

        courierCompanyRepo.findById(orderDetailDTOV2.getCourierCompanyDTO().getCode()).orElseThrow(()->{
            throw new NotFoundException("Record not found");
        });

        try {
            orderDetailRepoV2.save(mapper.map(orderDetailDTOV2, OrderDetailV2.class));
        }catch (MappingException e){
            List<ErrorMessage> errorMessages= new ArrayList<>(e.getErrorMessages());
            throw new MappingException(errorMessages);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return orderDetailDTOV2;
    }

    @Override
    public void deleteOrder(Long id) throws Exception {

        if(null == id){
            throw new ValidateException("Order id required");
        }

        orderDetailRepoV2.findById(id).orElseThrow(()->{
            throw new NotFoundException("Record not found");
        });

       try {
           orderDetailRepoV2.deleteById(id);
       }catch (Exception e){
           throw new Exception(e.getMessage());
       }


    }

    @Override
    public List<OrderDetailDTOV2> getOrders() throws NotFoundException {
        List<OrderDetailV2> orderDetails= orderDetailRepoV2.findAll();
        if (orderDetails.isEmpty()){
            throw new NotFoundException("Record not found");
        }else {
            return mapper.map(orderDetails,new TypeToken<ArrayList<OrderDetailDTOV2>>(){}.getType());
        }
    }
}
