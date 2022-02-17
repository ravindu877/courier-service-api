package lk.courierapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderDetailV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @NonNull
    private String senderName;
    @NonNull
    private String senderAddress;
    @NonNull
    private String receiverName;
    @NonNull
    private String receiverAddress;
    @NonNull
    private double parcelWeight;
    @NonNull
    private String parcelType;

    @ManyToOne
    private CourierCompany courierCompany;

}
