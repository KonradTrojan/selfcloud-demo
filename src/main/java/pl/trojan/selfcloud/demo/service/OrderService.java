package pl.trojan.selfcloud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.exception.http.notfound.OrderNotFoundException;
import pl.trojan.selfcloud.demo.mapper.OrderMapper;
import pl.trojan.selfcloud.demo.model.Order;
import pl.trojan.selfcloud.demo.model.dto.OrderDto;
import pl.trojan.selfcloud.demo.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(final OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order getOrder(final long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order " + id + " not found.");
        }
        return order.get();
    }

    public List<Order> getAllOrders(){
        List<Order> allOrders = new ArrayList<>();
        orderRepository.findAll().forEach(allOrders::add);
        return allOrders;
    }

    public Order createOrder(final OrderDto orderDto){
        return orderRepository.save(OrderMapper.mapToOrder(orderDto));
    }

    public Order updateOrder(final long id, final OrderDto orderDto){

        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            return orderRepository.save(OrderMapper.mapToOrder(id, orderDto));
        }else {
            throw new OrderNotFoundException("Order with id " + id + "not found.");
        }
    }


    public void deleteOrder(final long id){
        orderRepository.deleteById(id);
    }
}
