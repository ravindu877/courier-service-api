package lk.courierapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CourierCompany {
    @Id
    private String code;
    private String name;
    private String address;
    private String email;
    private String contact;

    @OneToMany(mappedBy = "courierCompany", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
