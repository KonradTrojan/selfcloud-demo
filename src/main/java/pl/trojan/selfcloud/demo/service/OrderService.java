package pl.trojan.selfcloud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import pl.trojan.selfcloud.demo.exception.OrderNotFound;
import pl.trojan.selfcloud.demo.model.Order;
import pl.trojan.selfcloud.demo.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(final OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order getOrder(final long id){
        return orderRepository.findById(id).orElseThrow(OrderNotFound::new);
    }

    public List<Order> getAllOrders(){
        List<Order> allOrders = new ArrayList<>();
        orderRepository.findAll().forEach(allOrders::add);
        return allOrders;
    }

    public Order createOrder(final Order order){
        return orderRepository.save(order);
    }

    public Order updateOrder(final long id,final Order order){
        return orderRepository.save(order);
    }


    public void deleteOrder(final long id){
        orderRepository.deleteById(id);
    }
}
