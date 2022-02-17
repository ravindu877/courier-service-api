package lk.courierapi.repository;

import lk.courierapi.entity.CourierCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierCompanyRepo extends JpaRepository<CourierCompany,String> {

    CourierCompany findByCode(String code);

}
