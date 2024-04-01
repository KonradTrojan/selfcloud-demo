package pl.trojan.selfcloud.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.trojan.selfcloud.demo.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
