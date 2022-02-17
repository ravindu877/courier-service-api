package lk.courierapi.repository;

import lk.courierapi.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail,Long> {
}
