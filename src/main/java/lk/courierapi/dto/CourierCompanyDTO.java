package lk.courierapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierCompanyDTO {
    private String code;
    private String name;
    private String address;
    private String email;
    private String contact;
}
