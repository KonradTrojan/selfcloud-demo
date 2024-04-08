package pl.trojan.selfcloud.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.trojan.selfcloud.demo.exception.http.notfound.OrderNotFoundException;
import pl.trojan.selfcloud.demo.exception.http.notfound.UserNotFoundException;
import pl.trojan.selfcloud.demo.model.User;
import pl.trojan.selfcloud.demo.service.mapper.OrderMapper;
import pl.trojan.selfcloud.demo.model.Order;
import pl.trojan.selfcloud.demo.model.dto.OrderDto;
import pl.trojan.selfcloud.demo.repository.OrderRepository;
import pl.trojan.selfcloud.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(final OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public OrderDto getOrder(final long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order " + id + " not found.");
        }
        return OrderMapper.mapToOrderDto(order.get());
    }

    public List<OrderDto> getAllOrders(){
        List<OrderDto> allOrders = new ArrayList<>();
        orderRepository.findAll().forEach(order ->
                allOrders.add(OrderMapper.mapToOrderDto(order)));
        return allOrders;
    }

    public OrderDto createOrder(final OrderDto orderDto){
        Optional<User> user = userRepository.findByUsername(orderDto.getUserDto().getUsername());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Order with username " + orderDto.getUserDto().getUsername() + "not found.");
        }
        Order order = orderRepository.save(OrderMapper.mapToOrder(orderDto, user.get()));
        return OrderMapper.mapToOrderDto(order);
    }

    public OrderDto updateOrder(final long id, final OrderDto orderDto){

        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()){
            throw new OrderNotFoundException("Order with id " + id + "not found.");
        }
        Order savedOrder = orderRepository.save(OrderMapper.mapToOrder(id, orderDto));
        return OrderMapper.mapToOrderDto(savedOrder);
    }


    public void deleteOrder(final long id){
        orderRepository.deleteById(id);
    }
}
